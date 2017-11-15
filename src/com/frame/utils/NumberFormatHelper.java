package com.frame.utils;

import java.text.DecimalFormat;

/**
 * 描述：日期工具类
 * 版权：Copyright (c) 2016
 * 时间：2016/8/1 14:22
 * 公司：中国联通
 * 作者：李新
 * 版本：1.0
 */
public class NumberFormatHelper
{



    /**
     *@描述: 四舍五入数字
     *@param scale 位数
     *@param num   数字
     *@return
     */
    public static String roundNumber(double num, int scale)
    {
        String formatStr = "0.";
        for (int i = 0; i < scale; i++)
        {
            formatStr += "0";
        }
        DecimalFormat df = new DecimalFormat(formatStr);
        String strNum = df.format(num);

        return strNum;
    }



    /**
     *@描述: 截尾数字
     *@param scale
     *@param num
     *@return
     */
    public static String truncNumber(double num, int scale)
    {
        String formatStr = "0.";
        for (int i = 0; i <= scale; i++)
        {
            formatStr += "0";
        }
        DecimalFormat df = new DecimalFormat(formatStr);
        String strNum = df.format(num);
        strNum = strNum.substring(0, strNum.length() - 1);
        return strNum;
    }


    public static void main(String[] args)
    {
        double test = 123.5678;
        System.out.println(roundNumber(test, 3));
        System.out.println(truncNumber(test, 3));
    }

    /**
     * 描述: 对数据进行格式化
     * @param price
     * @param pattern
     * @return
     */

    public static double formatPrice(Double price, String pattern)
    {
        try
        {
            //float temp = Float.parseFloat(price);
            DecimalFormat df = new DecimalFormat(pattern);
            return Double.valueOf(df.format(price));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }


    public static double formatPrice(Double price)
    {
        try
        {
            DecimalFormat df = new DecimalFormat("0.00");
            return Double.valueOf(df.format(price));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * 描述: 对数据进行格式化
     *
     * @param price
     * @param pattern
     * @return
     */

    public static double formatPrice(Float price, String pattern)
    {
        try
        {
            // float temp = Float.parseFloat(price);
            DecimalFormat df = new DecimalFormat(pattern);
            return Double.valueOf(df.format(price));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }


    public static double formatPrice(Float price)
    {
        try
        {
            // float temp = Float.parseFloat(price);
            DecimalFormat df = new DecimalFormat("0.00");
            return Double.valueOf(df.format(price));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }
}
