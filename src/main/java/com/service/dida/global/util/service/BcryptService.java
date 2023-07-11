package com.service.dida.global.util.service;

import com.service.dida.global.util.usecase.BcryptUseCase;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
public class BcryptService implements BcryptUseCase {

    public String encrypt(String pwd) {
        return BCrypt.hashpw(pwd, BCrypt.gensalt());
    }

    public boolean isMatch(String pwd, String hashed) {
        return BCrypt.checkpw(pwd,hashed);
    }
}
