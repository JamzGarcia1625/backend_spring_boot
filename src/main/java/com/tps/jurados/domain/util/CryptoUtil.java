package com.tps.jurados.domain.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CryptoUtil {
    private final Log logger = LogFactory.getLog(getClass());
    private static final String AES = "AES";
    private static final byte[] IV_SPEC = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00 };

    @Value("${crypto.secret}") private String secret;
    @Value("${crypto.algorithm}") private String algorithm;

    public String encrypt(String value) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes  = new byte[16];
        random.nextBytes(randomBytes);

        try {
            GCMParameterSpec iv   = new GCMParameterSpec(128, randomBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            byte[] encryptedBytes = Base64.getUrlEncoder().encode(encrypted);
            return new String(encryptedBytes, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
                 | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            logger.error("Error encrypting: " + ex.getMessage());
            return null;
        }
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(IV_SPEC);
            SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8));
            byte[] decrypted = cipher.doFinal(encryptedBytes);
            return new String(decrypted);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
                 | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            logger.error("Error decrypting: " + ex.getMessage());
            return null;
        }
    }



    /**
     * Permite transformas un string en base64 en su contenido original.
     *
     * @param str
     * @return
     */
    public String base64ToRaw(String str) {
        byte[] bytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
