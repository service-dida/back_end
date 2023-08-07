package com.service.dida.global.util.usecase;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface RsaUseCase {
    KeyPair generateKeypair() throws NoSuchAlgorithmException;

    String rsaEncode(String plainText, String publicKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    String rsaDecode(String encryptedPlainText, String privateKey)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException;

    String base64EncodeToString(byte[] byteData);
}
