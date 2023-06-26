package com.service.dida.domain.member.contorller;

import com.service.dida.domain.member.dto.MemberResponseDto.MemberDetailInfo;
import com.service.dida.domain.member.dto.MemberResponseDto.OtherMemberDetailInfo;
import com.service.dida.domain.member.dto.MemberResponseDto.WalletExists;
import com.service.dida.domain.member.dto.SendAuthEmailDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.usecase.GetMemberUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMemberController {

    private final GetMemberUseCase getUserUseCase;

    /**
     * 지갑 보유 여부 확인하기
     */
    @GetMapping("/common/wallet")
    public ResponseEntity<WalletExists> isExistWallet(@CurrentMember Member member) {
        return new ResponseEntity<>(getUserUseCase.isExistWallet(member), HttpStatus.OK);
    }

    /**
     * 인증 메일 보내기 Api
     */
    @GetMapping("/visitor/auth")
    public ResponseEntity<SendAuthEmailDto> sendAuthEmail(@CurrentMember Member member) {
        return new ResponseEntity<>(getUserUseCase.sendAuthMail(member), HttpStatus.OK);
    }

    /**
     * 내 프로필 확인하기 Api
     */
    @GetMapping("/common/profile")
    public ResponseEntity<MemberDetailInfo> getProfileDetailInfo(@CurrentMember Member member) {
        return new ResponseEntity<>(getUserUseCase.getMemberDetailInfo(member), HttpStatus.OK);
    }

    /**
     * 다른 유저 프로필 확인하기 Api
     */
    @GetMapping("/common/profile/{memberId}")
    public ResponseEntity<OtherMemberDetailInfo> getOtherProfileDetailInfo(
        @CurrentMember Member member, @PathVariable(name = "memberId") Long memberId) {
        return new ResponseEntity<>(getUserUseCase.getOtherMemberDetailInfo(member, memberId),
            HttpStatus.OK);
    }
}
