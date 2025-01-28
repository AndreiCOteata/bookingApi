package com.example.application.common.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailMapper emailMapper;

    @Async
    public void sendEmail(String mail, EmailMessage message) throws MessagingException{
        send(mail, message.getSubject(), message.getAsHtml(emailMapper), null, null);
    }
    public void send(String email, String subject, String bodyText, DataSource data, String filename) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true , "UTF-8");
        helper.setTo(email);
        if(data!=null)
            helper.addAttachment(filename+".ics", data);
        helper.setSubject(subject);
        helper.setText(bodyText, true);
        javaMailSender.send(message);
    }
}
