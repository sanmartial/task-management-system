package org.globaroman.taskmanagementsystem.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.globaroman.taskmanagementsystem.exception.DataProcessingException;
import org.globaroman.taskmanagementsystem.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Value("${mail.smtp.host}")
    private String smptHost;
    @Value("${mail.smtp.port}")
    private String port;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${mail.login}")
    private String userName;
    @Value("${mail.password}")
    private String password;

    @Override
    public void sendEmail(String from, String to, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smptHost);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new DataProcessingException("Email didn't send", e);
        }
    }
}
