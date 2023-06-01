package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.MemberResponseDto.WalletExists;
import com.service.dida.domain.member.dto.SendAuthEmailDto;
import com.service.dida.domain.member.entity.Member;

public interface GetMemberUseCase {

    SendAuthEmailDto sendAuthMail(Member member);

    WalletExists isExistWallet(Member member);
}
