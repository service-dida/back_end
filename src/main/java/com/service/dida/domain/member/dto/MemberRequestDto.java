package com.service.dida.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialLoginToken {
        @NotBlank(message = "ID Token이 없습니다.")
        private String idToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterMember {
        @NotBlank(message = "이메일 형식이 아닙니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        private String nickname;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckNickname {
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        private String nickname;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateDeviceToken {
        @NotBlank(message = "Device token은 공백일 수 없습니다.")
        private String deviceToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProfile {
        private String descriptionAndImg;
    }
}
