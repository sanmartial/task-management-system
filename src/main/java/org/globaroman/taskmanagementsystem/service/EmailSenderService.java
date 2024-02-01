package org.globaroman.taskmanagementsystem.service;

public interface EmailSenderService {
    void sendEmail(String from, String to, String subject, String body);
}
