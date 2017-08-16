package com.mqoo.platform.xop.common.util.encryption;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {

    private static final int RSA512_KEY_SIZE = 512;

    private static final String KEY_ALGORITHM = "RSA";
    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    public static String[] genRsa512PublicPrivateKeys() {
        return genRsaPublicPrivateKeys(RSA512_KEY_SIZE);
    }

    public static String[] genRsaPublicPrivateKeys(int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(keySize);
        KeyPair keyPair = keyGen.genKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        String publicKeyBase64 = Enc.encode64(publicKey);
        String privateKeyBase64 = Enc.encode64(privateKey);
        return new String[] {publicKeyBase64, privateKeyBase64};
    }

    public static PrivateKey privateKey(byte[] privateKeyBytes) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            return kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey publicKey(byte[] publicKeyBytes) {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            return kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey privateKeyFromPEM(String pemEncodedPrivateKey) {
        return privateKey(Enc.decode64(decodePem(pemEncodedPrivateKey)));
    }

    public static PublicKey publicKeyFromPEM(String pemEncodedPublicKey) {
        return publicKey(Enc.decode64(decodePem(pemEncodedPublicKey)));
    }

    protected static String decodePem(String pemEncodedKey) {
        return pemEncodedKey.replaceAll(
                "(-+BEGIN (RSA )?(PRIVATE|PUBLIC) KEY-+|-+END (RSA )?(PRIVATE|PUBLIC) KEY-+|\r|\n)",
                "");
    }

    public static String privateKeyEncrypt(String privateKey, String value) {
        return Enc.encode64(privateEncrypt(Enc.decode64(privateKey), Enc.getBytes(value)));
    }

    public static String publicKeyEncrypt(String publicKey, String value) {
        return Enc.encode64(publicEncrypt(Enc.decode64(publicKey), Enc.getBytes(value)));
    }

    public static String privateKeyDecrypt(String privateKey, String encrypted) {
        return Enc.getString(privateDecrypt(Enc.decode64(privateKey), Enc.decode64(encrypted)));
    }

    public static String publicKeyDecrypt(String publicKey, String encrypted) {
        return Enc.getString(publicDecrypt(Enc.decode64(publicKey), Enc.decode64(encrypted)));
    }

    public static byte[] privateEncrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.ENCRYPT_MODE, keyBytes, true, messageBytes);
    }

    public static byte[] publicEncrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.ENCRYPT_MODE, keyBytes, false, messageBytes);
    }

    public static byte[] privateDecrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.DECRYPT_MODE, keyBytes, true, messageBytes);
    }

    public static byte[] publicDecrypt(final byte[] keyBytes, final byte[] messageBytes) {
        return transform(Cipher.DECRYPT_MODE, keyBytes, false, messageBytes);
    }

    private static byte[] transform(final int mode, final byte[] keyBytes, boolean isPrivateKey,
            final byte[] messageBytes) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(mode, isPrivateKey ? privateKey(keyBytes) : publicKey(keyBytes));
            return cipher.doFinal(messageBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
