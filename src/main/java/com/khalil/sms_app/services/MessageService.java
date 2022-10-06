package com.khalil.sms_app.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.khalil.sms_app.models.User;
import com.khalil.sms_app.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.khalil.sms_app.models.Message;
import com.khalil.sms_app.models.MessageBatch;
import com.khalil.sms_app.repositories.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> saveAllMessage(List<Message> messages) {

        messageRepository.saveAll(messages);
        return messages;
    }

    public void sendMessageToEmployee(MessageBatch messageBatch) {
        System.out.println("batch id =" + messageBatch.getId());
        List<Message> messages = messageRepository.findMessageByMessageBatch(messageBatch);
        for (Message message : messages) {
            System.out.println(message.getMessageContent());
            String phone = null;
            if(message.getEmployee() != null){
             phone =  message.getEmployee().getPhoneNumber();
            }else{
                phone = message.getPhoneNumber();
            }
           // System.out.println("hello"+ message.getMessageContent());
           
           // "http://127.0.0.1:13013/cgi-bin/sendsms?username=${KID}&password=${KPASS}&from=8404&charset=UTF-8&coding=2&to=${mobile}
//            String ur = "http://10.1.125.98:13013/cgi-bin/sendsms?username=kan&password=kan&from=8404&charset=UTF-8&coding=2&to="
//                    + phone + "&text="+strip(strip(message.getMessageContent()));
            //&dlr-url=http://localhost:8080/api/v1/messages/changeStatus?id="+message.getId()+"&state%3D%25d%26msisdn%3D%25p
//            String ur1 = "http://10.1.125.98:13013/cgi-bin/sendsms?username=kan&password=kan&from=8404&dlr-mask=31&charset=UTF-8&coding=2&dlr-url=http://localhost:8080/api/v1/messages/changeStatus?"+message.getId()+"&to="
//                    + phone + "&text="+strip(strip(message.getMessageContent()));

            String ur = "http://10.1.125.98:13013/cgi-bin/sendsms?username=kan&password=kan&from=8404&dlr-mask=31&charset=UTF-8&coding=2&dlr-url=https://10.1.125.99:8080/api/v1/messages/changeStatus/"+message.getId()+"?state%3D%25d%26msisdn%3D%25p&to="
                    + phone + "&text="+strip(strip(message.getMessageContent()));

            System.out.println(ur);
            try {
                URL url = new URL(ur);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                for (int i = 1; i <= 8; i++) {
                    System.out.println(huc.getHeaderFieldKey(i) + " = " + huc.getHeaderField(i));
                }
                huc.disconnect();
                System.out.println("success");
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

    private String strip(String messageContent) {
        if(messageContent==null){
            return  messageContent;
        }
        return messageContent.replace(" ", "%20").replace("\\n","%0A");
    }

    public List<Message> getAllMessages(List<User> users) {
        return messageRepository.findAllByUser(users);
    }

    public Message findMessage(Integer id) {
        return messageRepository.findById(id).get();
    }

    public ResponseEntity<?> saveMessage(Message message) {
        messageRepository.save(message);
        return ResponseEntity.ok().body(new MessageResponse("Success"));
    }
}
