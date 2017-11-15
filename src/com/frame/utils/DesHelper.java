package com.frame.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.Key;
import java.security.Security;

/**
 * 描述：Des加密解密工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/2/16 14:15
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class DesHelper {
    private static String strDefaultKey = "";// 默认秘钥
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * 默认构造函数(使用默认秘钥)
     */
    public DesHelper() {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造函数
     *
     * @param strKey 密钥
     */
    public DesHelper(String strKey) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key;
        try {
            key = getKey(strKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定字符串生成密钥 (密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位)
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     */
    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    /**
     * 加密strIn
     *
     * @param strIn 需要加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String strIn) {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密 strIn
     *
     * @param strIn 需要解密的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String strIn) {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需要加密的字节数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] arrB) {
        byte[] s = null;
        try {
            s = encryptCipher.doFinal(arrB);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需要解密的字节数组
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] arrB) {
        byte[] s = null;
        try {
            s = decryptCipher.doFinal(arrB);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB type数组
     * @return 转换成16进制字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[]
     * arrB)互为可逆的转换过程
     *
     * @param strIn 16进制字符串
     * @return byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}
