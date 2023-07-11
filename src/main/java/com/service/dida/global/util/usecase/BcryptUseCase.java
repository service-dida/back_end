package com.service.dida.global.util.usecase;

public interface BcryptUseCase {
    String encrypt(String pwd);

    boolean isMatch(String pwd, String hashed);
}
