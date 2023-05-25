package com.service.dida.domain.user.contorller;

import com.service.dida.domain.user.dto.UserResponseDto;
import com.service.dida.domain.user.usecase.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateUserController {

    private final UpdateUserUseCase updateUserUseCase;

    /**
     * 로그인 토큰 갱신
     */
    @PatchMapping("/common/refresh")
    public ResponseEntity<UserResponseDto.TokenInfo> refreshLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(updateUserUseCase.refreshAccessToken(authentication),
            HttpStatus.OK);
    }
}
