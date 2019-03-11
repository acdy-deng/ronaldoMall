package com.cheery.util;

import java.util.Random;

/**
 * @desc: 获取otp验证码
 * @className: OtpUtil
 * @author: RONALDO
 * @date: 2019-03-09 22:19
 */
public class OtpUtil {

    public static String otp() {
        Random random = new Random();
        int i = random.nextInt(99999);
        i += 10000;
        return String.valueOf(i);
    }

}
