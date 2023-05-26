package com.service.dida.domain.member.contorller;

import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.usecase.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMemberController {

    private final UpdateMemberUseCase updateMemberUseCase;

    /**
     * 로그인 토큰 갱신
     */
    @PatchMapping("/common/refresh")
    public ResponseEntity<MemberResponseDto.TokenInfo> refreshLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(updateMemberUseCase.refreshAccessToken(authentication),
            HttpStatus.OK);
    }
}
