package com.frame.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 描述：对象序列化工具
 * 版权：Copyright (c) 2015
 * 时间：2015/1/26 10:41
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SerializeHelper {
    /**
     * 序列化
     *
     * @param object 要序列化的对象
     * @return 序列字节数组
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes 序列字节数组
     * @return 对象
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return bytes;
        }
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
