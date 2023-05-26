package com.service.dida.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialLoginToken {
        @NotEmpty(message = "ID Token이 없습니다.")
        private String idToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterMember {
        @NotEmpty(message = "이메일 형식이 아닙니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @NotEmpty(message = "닉네임은 공백일 수 없습니다.")
        private String nickname;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckNickname {
        @NotEmpty(message = "닉네임은 공백일 수 없습니다.")
        private String nickname;
    }
}
