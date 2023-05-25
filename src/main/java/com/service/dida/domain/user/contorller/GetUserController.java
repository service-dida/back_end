package com.service.dida.domain.user.contorller;

import com.service.dida.domain.user.dto.SendAuthEmailDto;
import com.service.dida.domain.user.usecase.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUserController {

    private final GetUserUseCase getUserUseCase;

    @GetMapping("/visitor/auth")
    public ResponseEntity<SendAuthEmailDto> sendAuthEmail() {
        return new ResponseEntity<>(
            getUserUseCase.sendAuthMail(SecurityContextHolder.getContext().getAuthentication()),
            HttpStatus.OK);
    }
}