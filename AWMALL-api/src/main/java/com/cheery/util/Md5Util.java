package com.cheery.util;

import com.cheery.common.Constant;

import java.security.MessageDigest;

/**
 * @desc: MD5工具类
 * @className: Md5Util
 * @author: RONALDO
 * @date: 2019-02-23 23:37
 */
public class Md5Util {

    private static final String HEXDIGITS[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

    /**
     * 返回大写MD5
     *
     * @param origin
     * @param charsetname
     * @return
     */
    private static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodeUtf8(String origin) {
        origin = origin + "ronaldo78eqd+./aqj23ou89ZXcj@#$@#$#@KJdjklj;D../dSF.,";
        return MD5Encode(origin, Constant.ENCODING);
    }

}
