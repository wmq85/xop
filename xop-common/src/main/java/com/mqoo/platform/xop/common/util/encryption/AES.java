package com.mqoo.platform.xop.common.util.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES Encryption Utility<br/>
 * In order to support 256 key length, download JCE for java8 from the following URL, and extract
 * the jar files from the zip and save them in ${java.home}/jre/lib/security/:
 * http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
 * 
 * @created 2016-12-28
 */
public class AES {

    private static final int AES256_IV_LENGHT = 16;

    private static final int AES256_KEY_LENGTH = 256;

    private static final String AES_ALGORITHM = "AES";

    private static final String AES_CIPHER = "AES/CBC/PKCS5Padding";

    /** AES256 16 bytes zero iv */
    private static byte[] AES256_ZERO_IV = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static String encrypt(String key, String initVector, String value) {
        byte[] encrypted =
                        encrypt(Enc.decode64(key), Enc.decode64(initVector), Enc.getBytes(value));
        return Enc.encode64(encrypted);
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        byte[] decrypted = decrypt(Enc.decode64(key), Enc.decode64(initVector),
                        Enc.decode64(encrypted));
        return Enc.getString(decrypted);
    }

    public static String encrypt(String key, String value) {
        byte[] encrypted = encrypt(Enc.decode64(key), Enc.getBytes(value));
        return Enc.encode64(encrypted);
    }

    public static String decrypt(String key, String encrypted) {
        byte[] decrypted = decrypt(Enc.decode64(key), Enc.decode64(encrypted));
        return Enc.getString(decrypted);
    }

    public static byte[] encrypt(final byte[] keyBytes, final byte[] ivBytes,
                    final byte[] messageBytes) {
        return transform(Cipher.ENCRYPT_MODE, keyBytes, ivBytes, messageBytes);
    }

    public static byte[] decrypt(final byte[] keyBytes, final byte[] ivBytes,
                    final byte[] messageBytes) {
        return transform(Cipher.DECRYPT_MODE, keyBytes, ivBytes, messageBytes);
    }

    public static byte[] encrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.ENCRYPT_MODE, keyBytes, messageBytes);
    }

    public static byte[] decrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.DECRYPT_MODE, keyBytes, messageBytes);
    }

    private static byte[] transform(final int mode, final byte[] keyBytes, final byte[] ivBytes,
                    final byte[] messageBytes) {
        final SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        final IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        try {
            final Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(mode, keySpec, ivSpec);
            return cipher.doFinal(messageBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] transform(final int mode, final byte[] keyBytes,
                    final byte[] messageBytes) {
        return transform(mode, keyBytes, AES256_ZERO_IV, messageBytes);
    }

    public static String[] genAes256KeyAndIv() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] ivBytes = new byte[AES256_IV_LENGHT];
            secureRandom.nextBytes(ivBytes);
            String iv = Enc.encode64(ivBytes);

            KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);

            keyGen.init(AES256_KEY_LENGTH, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            String secret = Enc.encode64(secretKey.getEncoded());

            return new String[] {secret, iv};
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
