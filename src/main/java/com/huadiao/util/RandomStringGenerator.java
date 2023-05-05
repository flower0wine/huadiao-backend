package com.huadiao.util;

import java.util.Random;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 随机字符串生成器
 */
public class RandomStringGenerator {

    private static final String RANDOM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 生成指定长度的字符串
     * @param length 字符串长度
     * @return 返回生成的字符串
     */
    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int index = 0; index < length; index++) {
            stringBuilder.append(RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length())));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成字符串长度在范围 1 - maxLength 的随机字符串
     * @param maxLength 最大长度
     * @return 返回随即字符串
     */
    public static String generateRandomMaxLengthString(int maxLength) {
        Random random = new Random(maxLength + 1);
        return generateRandomString(random.nextInt(maxLength));
    }

}
