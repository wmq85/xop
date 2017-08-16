package com.mqoo.platform.xop.common.util.encryption;

import java.nio.charset.Charset;
import java.util.Base64;

public class Enc {

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final Charset CHARSET = Charset.forName(CHARSET_UTF8);

    public static byte[] getBytes(String str) {
        return str.getBytes(CHARSET);
    }

    public static String getString(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    public static String encode64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String encode64(String str) {
        return encode64(getBytes(str));
    }

    public static byte[] decode64(String base64EncodedString) {
        return Base64.getDecoder().decode(base64EncodedString);
    }

    public static String decode64AsStr(String base64EncodedString) {
        return new String(decode64(base64EncodedString), CHARSET);
    }

    public static String toRadix62(long number) {
        return Radix62.encode(number);
    }

    public static long parseRadix62(String s) {
        return Radix62.decode(s);
    }

    public static String compressHexTo62(String source) {
        return compress62(source, 16, 8);
    }

    public static String compress36To62(String source) {
        return compress62(source, 36, 8);
    }

    public static String compress62(String source, int sourceRadix, int compressLength) {
        StringBuffer sourceBuf = new StringBuffer(source);
        StringBuffer targetBuf = new StringBuffer();
        int start = 0;
        int end = 0;
        int length = source.length();
        while (length > start) {
            end = Integer.min(start + compressLength, length);
            long i = Long.parseLong(sourceBuf.substring(start, end), sourceRadix);
            targetBuf.append(toRadix62(i));
            start += compressLength;
        }
        return targetBuf.toString();
    }

    private static class Radix62 {

        private static final char[] digitsChar =
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        private static final int BASE = digitsChar.length;
        private static final int FAST_SIZE = 'z';
        private static final int[] digitsIndex = new int[FAST_SIZE + 1];

        static {
            for (int i = 0; i < FAST_SIZE; i++) {
                digitsIndex[i] = -1;
            }
            for (int i = 0; i < BASE; i++) {
                digitsIndex[digitsChar[i]] = i;
            }
        }

        public static long decode(String s) {
            long result = 0L;
            long multiplier = 1;
            for (int pos = s.length() - 1; pos >= 0; pos--) {
                int index = getIndex(s, pos);
                result += index * multiplier;
                multiplier *= BASE;
            }
            return result;
        }

        public static String encode(long number) {
            if (number < 0)
                throw new IllegalArgumentException("Number(Radix62) must be positive: " + number);
            if (number == 0)
                return "0";
            StringBuilder buf = new StringBuilder();
            while (number != 0) {
                buf.append(digitsChar[(int) (number % BASE)]);
                number /= BASE;
            }
            return buf.reverse().toString();
        }

        private static int getIndex(String s, int pos) {
            char c = s.charAt(pos);
            if (c > FAST_SIZE) {
                throw new IllegalArgumentException("Unknow character for Radix62: " + s);
            }
            int index = digitsIndex[c];
            if (index == -1) {
                throw new IllegalArgumentException("Unknow character for Radix62: " + s);
            }
            return index;
        }
    }

}
