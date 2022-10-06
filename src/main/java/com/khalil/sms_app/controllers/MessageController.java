package com.khalil.sms_app.controllers;

import com.khalil.sms_app.models.*;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.repositories.BatchRepository;
import com.khalil.sms_app.repositories.MessageRepository;
import com.khalil.sms_app.services.MessageService;
import com.khalil.sms_app.services.PdfExportService;
import com.khalil.sms_app.services.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://10.1.11.145:4200", maxAge = 360, allowCredentials = "true")
@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PdfExportService exportService;

    public MessageController(MessageService messageService, UserService userService, BatchRepository batchRepository, MessageRepository messageRepository, PdfExportService exportService) {
        this.messageService = messageService;
        this.userService = userService;
        this.batchRepository = batchRepository;
        this.messageRepository = messageRepository;
        this.exportService = exportService;
    }

    public MessageController() {

    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public List<Message> getAllMessages(Principal principal) {
        User user = userService.getUserByUserName(principal.getName());
        Division division = user.getEmployee().getDivision();
        System.out.println(division.getName());

        List<Division> divisions = new ArrayList<>(division.getChildren());
        List<Division> divisionList = new ArrayList<>(division.getChildren());
        for (int i = 0; i < divisionList.size(); i++) {
            divisions.addAll(divisionList.get(i).getChildren());
        }
        List<User> users = userService.findAllUserByDivision(divisions);
        users.add(user);
       return messageService.getAllMessages(users);
    }

    @PostMapping(value = "/uploadfile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> sendBulkMessages(@RequestBody MultipartFile file, Integer user_id) throws IOException {
        List<Message> messages = new ArrayList<>();
        User user;
        int count=0;
        MessageBatch messageBatch;
     
            try (XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream())) {
                user = userService.getUserById((user_id));
                LocalDateTime now = LocalDateTime.now();
                messageBatch = new MessageBatch(now);
                batchRepository.save(messageBatch);

                XSSFSheet sheet = wb.getSheetAt(0);

                XSSFRow header = sheet.getRow(0);
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);
                    Message message = new Message();
                    int numberOfCell = 0;
                    try {
                        numberOfCell = row.getPhysicalNumberOfCells();
                    } catch (Exception e) {
                       System.out.println("asan jira");
                    }

                    for (int j = 0; j < numberOfCell; j++) {
                        switch (header.getCell(j).toString()) {
                            case "phoneNumber":
                                try{
                                    message.setPhoneNumber(row.getCell(j).getRawValue());
                                }catch (NullPointerException nullPointerException){
                                    count++;
                                }
                                break;
                            case "messageContent":
                                try{
                                    message.setMessageContent(row.getCell(j).toString());
                                }catch (NullPointerException nullPointerException){
                                    count++;
                                }
                                break;
                            case "userName":
                                try{
                                    message.setUserName(row.getCell(j).toString());
                                }catch (NullPointerException ignored){
                                }
                            default:
                                break;
                        }
                    }
                    message.setMessageBatch(messageBatch);
                    message.setUser(user);
                    message.setStatus(0);
                    messages.add(message);
                }
            }
            if(count>=1){
                return ResponseEntity.badRequest().body(new MessageResponse(count+" row has null value please fill all column data and resend!!"));
            }
            messageService.saveAllMessage(messages);
        return ResponseEntity.ok().body(new MessageResponse("Message sent successfully! ",
                messages.get(0).getMessageBatch().getId()));
    }

    @GetMapping(value = "/batch_id")
    public ResponseEntity<?> sendMessageByBatch(@RequestParam Integer id) {
        MessageBatch messageBatch = batchRepository.findById(id).get();
        messageService.sendMessageToEmployee(messageBatch);
        return ResponseEntity.ok().body(new MessageResponse("success"));
    }

    @GetMapping(value="/changeStatus/{id}")
    public ResponseEntity<?> changeMessageStatus(@PathVariable("id") Integer id, @RequestParam Integer state){
        System.out.println("asan jira: "+state);
        Message message = messageService.findMessage(id);
        message.setStatus(state);
        return messageService.saveMessage(message);
    }

    @GetMapping(value = "exportPdf")
    public ResponseEntity<InputStreamResource> printPdf(Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        Division division = user.getEmployee().getDivision();
        System.out.println(division.getName());

        List<Division> divisions = new ArrayList<>(division.getChildren());
        List<Division> divisionList = new ArrayList<>(division.getChildren());
        for (int i = 0; i < divisionList.size(); i++) {
            divisions.addAll(divisionList.get(i).getChildren());
        }
        List<User> users = userService.findAllUserByDivision(divisions);
        users.add(user);
        List<Message> allMessages = messageService.getAllMessages(users);
        Map<User,List<Message>> messageMap = allMessages.stream().collect(Collectors.groupingBy(Message::getUser));
        System.out.println(messageMap);
       // return null;
       //PdfM pdfM = new PdfM(allMessages.get(0).getEmployee().getFatherName(),allMessages.size(),allMessages.size()*0.4);

        ByteArrayInputStream byteArrayInputStream = exportService.messagePdfReport(messageMap);
        HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Disposition", "inline, filename=smsReports.pdf");
       return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }


}
