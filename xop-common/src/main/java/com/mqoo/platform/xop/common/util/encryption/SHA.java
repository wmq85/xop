package com.mqoo.platform.xop.common.util.encryption;

import java.security.MessageDigest;

public class SHA {

    private static final String DIGEST_ALGORITHM_SHA256 = "SHA-256";

    private static final String DIGEST_ALGORITHM_SHA512 = "SHA-512";

    private static final SHA sha256 = new SHA(DIGEST_ALGORITHM_SHA256);

    private static final SHA sha512 = new SHA(DIGEST_ALGORITHM_SHA512);

    public static SHA getSha256() {
        return sha256;
    }

    public static SHA getSha512() {
        return sha512;
    }

    private String algorithm;

    private SHA() {}

    private SHA(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String hash(String value) {
        return Enc.encode64(hash(Enc.getBytes(value)));
    }

    public byte[] hash(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
