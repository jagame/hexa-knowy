package com.knowy.server.application.model;

public record MailMessage(String to, String subject, String body) {
}