package org.globaroman.taskmanagementsystem.service;

public interface EmailSenderService {
    String sendEmail(String from, String to, String subject, String body);
}
