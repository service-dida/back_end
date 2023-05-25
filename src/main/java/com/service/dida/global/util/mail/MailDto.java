package com.service.dida.global.util.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
    private String pwd;
}
