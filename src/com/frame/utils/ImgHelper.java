package com.frame.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 描述：图片工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/3/20 15:00
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class ImgHelper {
    /**
     * 将图片转成base64编码格式
     *
     * @param filePath 图片路径
     * @return 64位编码格式
     */
    public static String imgToBase64(String filePath) {
        if (StringHelper.isEmpty(filePath)) {
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        InputStream input = null;
        try {
            StringBuilder pictureBuffer = new StringBuilder();
            input = new FileInputStream(new File(filePath));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            for (int len = input.read(temp); len != -1; len = input.read(temp)) {
                out.write(temp, 0, len);
                pictureBuffer.append(encoder.encode(out.toByteArray()));
                out.reset();
            }

            return pictureBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将base64编码数据保存成图片
     *
     * @param base64   base64数据
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public static void base64ToImg(String base64, String filePath, String fileName) {
        if (StringHelper.isEmpty(base64) || StringHelper.isEmpty(filePath) || StringHelper.isEmpty(fileName)) {
        }else{
            BASE64Decoder decoder = new BASE64Decoder();
            FileOutputStream write = null;
            try {
                write = new FileOutputStream(new File("E:/test2.png"));
                byte[] decoderBytes = decoder.decodeBuffer(base64.toString());
                write.write(decoderBytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (write != null) {
                    try {
                        write.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
