package com.emotion.archive.utils;

import com.emotion.archive.constants.ConstValue;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    // AES 암호화
    public static String encryptAES(String value) {
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(ConstValue.AES_SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(encrypted));
        } catch (Exception e) {
            return value;
        }
    }

    // AES 복호화
    public static String decryptAES(String value) {
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(ConstValue.AES_SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipher.DECRYPT_MODE, sKeySpec);
            byte[] decrypted = cipher.doFinal(Base64.decodeBase64(value.getBytes()));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }

}
