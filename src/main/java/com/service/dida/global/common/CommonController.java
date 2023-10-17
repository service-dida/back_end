package com.service.dida.global.common;

import static com.service.dida.global.config.constants.ServerConstants.PRIVATE_KEY;
import static com.service.dida.global.config.constants.ServerConstants.PUBLIC_KEY;

import com.service.dida.global.common.dto.PublicKeyDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import com.service.dida.global.util.usecase.RsaUseCase;
import jakarta.annotation.PostConstruct;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final RsaUseCase rsaUseCase;

    @GetMapping("/access/denied")
    public String accessDenied() {
        throw new BaseException(GlobalErrorCode.ACCESS_DENIED);
    }

    @PostConstruct
    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaUseCase.generateKeypair();
        PUBLIC_KEY = rsaUseCase.base64EncodeToString(keyPair.getPublic().getEncoded());
        PRIVATE_KEY = rsaUseCase.base64EncodeToString(keyPair.getPrivate().getEncoded());
    }

    @GetMapping("/common/key")
    public ResponseEntity<PublicKeyDto> providePublicKey() {
        return new ResponseEntity<>(new PublicKeyDto(PUBLIC_KEY), HttpStatus.OK);
    }

    @GetMapping("/common/encode")
    public String test()
        throws NoSuchAlgorithmException,NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        return rsaUseCase.rsaEncode("000000",PUBLIC_KEY);
    }
}
