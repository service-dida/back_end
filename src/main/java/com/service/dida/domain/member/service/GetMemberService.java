package com.service.dida.domain.member.service;

import com.service.dida.domain.member.dto.MemberResponseDto.WalletExists;
import com.service.dida.domain.member.dto.SendAuthEmailDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.usecase.GetMemberUseCase;
import com.service.dida.global.util.mail.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMemberService implements GetMemberUseCase {

    private final MailUseCase mailUseCase;

    @Override
    public SendAuthEmailDto sendAuthMail(Member member) {
        return new SendAuthEmailDto(mailUseCase.sendAuthMail(member.getEmail()));
    }

    @Override
    public WalletExists isExistWallet(Member member) {
        if(member.getWallet() == null) return new WalletExists(false);
        else return new WalletExists(true);
    }
}
