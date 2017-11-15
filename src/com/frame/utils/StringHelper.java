package com.frame.utils;

/**
 * 描述：字符串工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 11:10
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class StringHelper {
    /**
     * 判断字符串是否为：""," ",null
     *
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否为：""," ",null
     *
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str) && str.trim().length() != 0;
    }


    /**
     * 截取字符串(beginStr到endStr之间的字符串)
     *
     * @param str      需要截取的字符串
     * @param beginStr 起始字符串
     * @param endStr   结束字符串
     * @return 截取后的字符串
     */
    public static String substring(String str, String beginStr, String endStr) {
        if (isEmpty(str) || isEmpty(beginStr) || isEmpty(endStr)) {
            return null;
        }
        return str.substring(str.indexOf(beginStr) + beginStr.length(), str.indexOf(endStr));
    }

    /**
     *
     * 描述：大写英文字母转换成小写
     *
     * @param strIn 字符串参数
     * @return
     */
    public static String toLowerStr(String strIn)
    {
        String strOut = new String(); //输出的字串
        int len = strIn.length(); //参数的长度
        int i = 0; //计数器
        char ch; //存放参数的字符

        while (i < len)
        {
            ch = strIn.charAt(i);

            if (ch >= 'A' && ch <= 'Z')
            {
                ch = (char) (ch - 'A' + 'a');
            }

            strOut += ch;
            i++;
        }
        return strOut;
    }
}
