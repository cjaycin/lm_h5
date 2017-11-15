package com.frame.utils;

import com.thinkive.base.util.UUID;
import com.thinkive.base.util.security.MD5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述：
 * 版权：Copyright (c) 2015
 * 时间：2016/08/17 11:04
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SiteLibrary {
    /**
     * 获取获取随机数
     * @return
     */
    public static String getSalt()
    {
        UUID result = UUID.randomUUID();
        String str=result.toString();
        str=UUID.getUUIDString(str);
        String salt=str.substring(str.length()-6, str.length());
        return salt;
    }

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String getPassword(String password,String salt)
    {
        MD5 md5=new MD5();
        password = md5.getMD5ofStr(md5.getMD5ofStr(password)+salt);
        return password;
    }

    /**
     * 手机号混淆
     * @param mobileno 手机号
     * @param size 混淆位数
     * @param reg   混淆字符
     * @return
     */
    public static String replaceMobile(String mobileno, int size, String reg){
        Random r = new Random();
        List<Integer> list = new ArrayList<Integer>();
        int strLength = mobileno.length();
        int i;
        while (list.size()<size){
            i= r.nextInt(strLength);
            if (i!=0 && i!=1 && i!=2 && !list.contains(i)){
                list.add(i);
            }
        }
        StringBuilder sb = new StringBuilder(mobileno);
        for (int j=0; j<size;j++){
            int temp = list.get(j);
            sb = sb.replace(temp,temp+1,reg);
        }
        return sb.toString();
    }
}
