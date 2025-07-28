package com.knowy.server.application.service.model;

public record MailMessage(String to, String subject, String body) {
}
