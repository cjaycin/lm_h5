package com.frame.utils;

import java.util.Random;

/**
 * 描述：生成随机数辅助类
 * 版权：Copyright (c) 2015
 * 时间：2015/2/25 15:18
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class RandomHelper {
    //生成随机字符时，字符来源
    private static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static int strLength = str.length();
    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandomStr(int length) {
        Random random = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++) {
            res.append(str.charAt(random.nextInt(strLength)));
        }
        return res.toString();
    }
}
