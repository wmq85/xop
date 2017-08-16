package com.mqoo.platform.xop.common.util.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Provide signature sign and verify utilities
 *
 * @since 2017-02-24 10:06
 */
public class Sign {

    public final static String SHA256withRSA = "SHA256withRSA";
    public final static String DEFAULT_SIGNATURE_ALGORITHM = SHA256withRSA;

    private Sign() {
    }

    public static Signature getSignature(String algorithm) {
        try {
            return Signature.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(Signature signature, PublicKey publicKey, byte[] dataBytes,
            byte[] signatureBytes) {
        try {
            signature.initVerify(publicKey);
            signature.update(dataBytes);
            return signature.verify(signatureBytes);
        } catch (InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String algorithm, PublicKey publicKey, String data,
            String signature) {
        return verify(getSignature(algorithm), publicKey, Enc.getBytes(data),
                Enc.decode64(signature));
    }

    public static boolean verify(String algorithm, String publicKey, String data,
            String signature) {
        return verify(algorithm, RSA.publicKey(Enc.decode64(publicKey)), data, signature);
    }

    public static boolean verify(String publicKey, String data, String signature) {
        return verify(DEFAULT_SIGNATURE_ALGORITHM, publicKey, data, signature);
    }

    public static byte[] signature(Signature signature, PrivateKey privateKey, byte[] dataBytes) {
        try {
            signature.initSign(privateKey);
            signature.update(dataBytes);
            return signature.sign();
        } catch (InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static String signature(String algorithm, String privateKey, String data) {
        return Enc.encode64(
                signature(getSignature(algorithm), RSA.privateKey(Enc.decode64(privateKey)),
                        Enc.getBytes(data)));
    }

    public static String signature(String privateKey, String data) {
        return signature(DEFAULT_SIGNATURE_ALGORITHM, privateKey, data);
    }



}
