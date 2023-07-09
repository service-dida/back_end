package com.service.dida.global.util.usecase;

public interface MailUseCase {

    String sendAuthMail(String email);

    String sendReportMail(String email);

    String sendPasswordMail(String email);
}
