package com.service.dida.global.util.mail;

public interface MailUseCase {
    String sendAuthMail(String email);
    String sendReportMail(String email);
}
