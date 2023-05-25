package com.service.dida.domain.user.usecase;

import com.service.dida.domain.user.dto.SendAuthEmailDto;
import org.springframework.security.core.Authentication;

public interface GetUserUseCase {

    SendAuthEmailDto sendAuthMail(Authentication authentication);
}
