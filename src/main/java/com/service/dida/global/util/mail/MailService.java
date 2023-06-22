package com.service.dida.global.util.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService implements MailUseCase {
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "DIDA";


    @Override
    public String sendAuthMail(String email) {
        return sendMail(createAuthMail(email));
    }

    @Override
    public String sendReportMail(String email) {
        return sendMail(createReportMemberMail(email));
    }

    public String getTmpPwd() {
        char[] numSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder pwd = new StringBuilder();
        int idx;
        for (int i = 0; i < 6; i++) {
            idx = (int) (numSet.length * Math.random());
            pwd.append(numSet[idx]);
        }
        return pwd.toString();
    }

    public MailDto createPwdMail(String email) {
        String pwd = getTmpPwd();
        String title = "DIDA 임시 결제 비밀번호";
        String message =
            "임시 결제 비밀번호는 " + pwd + " 입니다.\n" + "빠른 시간내로 결제 비밀번호 변경을 권장드립니다.\n" + "감사합니다.";
        MailDto mailDto = new MailDto(
            email,
            title,
            message,
            pwd
        );
        return mailDto;
    }

    public MailDto createAuthMail(String email) {
        String pwd = getTmpPwd();
        String title = "DIDA 이메일 인증 번호";
        String message = "이메일 인증 번호는 " + pwd + " 입니다.\n" + "감사합니다.";
        MailDto mailDto = MailDto.builder()
            .address(email)
            .title(title)
            .message(message)
            .pwd(pwd)
            .build();
        return mailDto;
    }

    public MailDto createReportMemberMail(String email) {
        String pwd = "";
        String title = "DIDA 신고 누적으로 인한 계정 임시 삭제 처리 안내";
        String message = "회원님의 계정이 신고 누적으로 인해 임시 삭제 처리 되었습니다." + "감사합니다.";
        return new MailDto(
            email, title, message, pwd
        );
    }

    public String sendMail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        javaMailSender.send(message);
        return mailDto.getPwd();
    }
}
