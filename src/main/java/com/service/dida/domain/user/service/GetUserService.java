package com.service.dida.domain.user.service;

import com.service.dida.domain.user.dto.SendAuthEmailDto;
import com.service.dida.domain.user.entity.User;
import com.service.dida.domain.user.repository.UserRepository;
import com.service.dida.domain.user.usecase.GetUserUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
import com.service.dida.global.util.mail.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepository userRepository;
    private final MailUseCase mailUseCase;

    @Override
    public SendAuthEmailDto sendAuthMail(Authentication authentication) {
        User user = userRepository.findByUserId((Long) authentication.getPrincipal()).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new BaseException(UserErrorCode.EMPTY_MEMBER);
        }
        return new SendAuthEmailDto(mailUseCase.sendAuthMail(user.getEmail()));
    }
}
