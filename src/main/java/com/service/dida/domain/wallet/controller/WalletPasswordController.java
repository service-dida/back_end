package com.service.dida.domain.wallet.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangePwd;
import com.service.dida.domain.wallet.usecase.WalletPasswordUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletPasswordController {

    private final WalletPasswordUseCase walletPasswordUseCase;

    /**
     * 임시 비밀번호 발급 Api
     */
    @PatchMapping("/member/password/tmp")
    public ResponseEntity<Integer> setTmpPassword(@CurrentMember Member member) {
        walletPasswordUseCase.setTmpPassword(member);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 비밀번호 변경하기(임시, 수정 필요)
     */
    @PatchMapping("/member/password")
    public ResponseEntity<Integer> changePassword(@CurrentMember Member member,
        @Valid @RequestBody ChangePwd changePwd) {
        walletPasswordUseCase.changePassword(member,changePwd);
        return new ResponseEntity<>(200,HttpStatus.OK);
    }
}
