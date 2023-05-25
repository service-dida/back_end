package com.service.dida.domain.user.usecase;

import com.service.dida.domain.user.dto.SendAuthEmailDto;
import org.springframework.security.core.Authentication;

public interface GetMemberUseCase {

    SendAuthEmailDto sendAuthMail(Authentication authentication);
}
