package com.huadiao.util;

import java.util.Random;

/**
 * 该类用于生成不同的 userId
 * @author flowerwine
 */
public class CreateHuadiaoUserId {

    private static final char[] NUMBER_CODE_UNIT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private final static int LENGTH = 12;

    /**
     * 创建一个花凋的 userId, 格式为 huadiao_xxx...
     * @return 返回花凋 userId
     */
    public static String createUserId(){
        StringBuffer origin = new StringBuffer("huadiao_");
        StringBuffer userId = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < LENGTH; i++){
            userId.append(NUMBER_CODE_UNIT[random.nextInt(NUMBER_CODE_UNIT.length)]);
        }
        return origin.append(userId).toString();
    }
}
