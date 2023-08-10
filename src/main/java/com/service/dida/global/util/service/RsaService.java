package com.service.dida.global.util.service;

import com.service.dida.global.util.usecase.RsaUseCase;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.stereotype.Service;

@Service
public class RsaService implements RsaUseCase {

    private static final String INSTANCE_TYPE = "RSA";

    @Override
    public KeyPair generateKeypair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(INSTANCE_TYPE);
        keyPairGenerator.initialize(2048, new SecureRandom());

        return keyPairGenerator.genKeyPair();
    }

    @Override
    public String rsaEncode(String plainText, String publicKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance(INSTANCE_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, convertPublicKey(publicKey));

        byte[] plainTextByte = cipher.doFinal(plainText.getBytes());

        return base64EncodeToString(plainTextByte);
    }

    public String rsaDecode(String encryptedPlainText, String privateKey)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

        byte[] encryptedPlainTextByte = Base64.getDecoder().decode(encryptedPlainText.getBytes());

        Cipher cipher = Cipher.getInstance(INSTANCE_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, convertPrivateKey(privateKey));

        return new String(cipher.doFinal(encryptedPlainTextByte));
    }

    private Key convertPublicKey(String publicKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(INSTANCE_TYPE);
        byte[] publicKeyByte = Base64.getDecoder().decode(publicKey.getBytes());

        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));
    }

    private Key convertPrivateKey(String privateKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(INSTANCE_TYPE);
        byte[] privateKeyByte = Base64.getDecoder().decode(privateKey.getBytes());

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
    }

    @Override
    public String base64EncodeToString(byte[] byteData) {
        return Base64.getEncoder().encodeToString(byteData);
    }
}
