package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.SendAuthEmailDto;
import org.springframework.security.core.Authentication;

public interface GetMemberUseCase {

    SendAuthEmailDto sendAuthMail(Authentication authentication);
}
