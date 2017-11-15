package com.frame.utils;

import java.security.MessageDigest;

/**
 * 描述：MD5工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 21:42
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class Md5Helper {

    /**
     * MD5加密
     *
     * @param s 加密字符串
     * @return 密文
     */
    public final static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5算法先大写然后MD5 然后在大写，再交换前16和后16位的为位置，在进行一次MD5所得到的值
     *
     * @param str 加密字符串
     * @return 密文
     */
    public final static String md5reverse(String str) {
        String firstMd5 = md5(str.toUpperCase()).toUpperCase();
        String left = firstMd5.substring(0, 16);
        String right = firstMd5.substring(16, 32);
        String secondMd5 = right + left;
        String reultMd5 = md5(secondMd5.toUpperCase());
        return reultMd5;
    }
}
