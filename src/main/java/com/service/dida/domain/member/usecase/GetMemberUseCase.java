package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.SendAuthEmailDto;
import com.service.dida.domain.member.entity.Member;
import org.springframework.security.core.Authentication;

public interface GetMemberUseCase {

    SendAuthEmailDto sendAuthMail(Member member);
}
