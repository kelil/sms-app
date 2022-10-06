package com.khalil.sms_app.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.khalil.sms_app.models.Message;
import com.khalil.sms_app.models.User;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PdfExportService {

    public ByteArrayInputStream messagePdfReport(Map<User, List<Message>> messages) {
        Document document  = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document,byteArrayOutputStream);
            document.open();

            //add text to pdf
            Font font = new Font(FontFactory.getFont(FontFactory.COURIER,14, BaseColor.BLACK));
            Paragraph paragraph = new Paragraph("Sms Reports",font);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(3);

            //make titles


            Streamable.of("Sender","Total Sent","Total Cost").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorder(1);
                    header.setPhrase(new Phrase(headerTitle,headFont));
                    table.addCell(header);
            });
            for(Map.Entry<User, List<Message>> entry: messages.entrySet()){
                PdfPCell sender = new PdfPCell(new Phrase((entry.getKey().getEmployee().getGivenName()+" "+entry.getKey().getEmployee().getFatherName())));
                sender.setPaddingLeft(1);
                sender.setVerticalAlignment(Element.ALIGN_MIDDLE);
                sender.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(sender);

                System.out.println("hello jarana: "+entry.getValue().size());
                PdfPCell total = new PdfPCell(new Phrase(String.valueOf(entry.getValue().size())));
                total.setPaddingLeft(1);
                total.setVerticalAlignment(Element.ALIGN_MIDDLE);
                total.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(total);

                PdfPCell cost = new PdfPCell(new Phrase(String.valueOf(entry.getValue().size()*2)));
                cost.setPaddingLeft(1);
                cost.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cost.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cost);
            }
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
