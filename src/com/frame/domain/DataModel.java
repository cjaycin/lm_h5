package com.frame.domain;


import com.frame.utils.StringHelper;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 描述：数据模型
 * 版权：Copyright (c) 2015
 * × 时间：2015/1/11 11:08
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class DataModel extends HashMap<String, Object> {
    private static final long serialVersionUID = 8286196885478407494L;

    /**
     * 获取int类型值
     *
     * @param key key
     * @return int类型值
     */
    public int getInt(String key) {
        int value = 0;
        if (StringHelper.isEmpty(key)) {
            return value;
        }

        if (!containsKey(key)) {
            return value;
        }

        Object obj = this.get(key);
        if (obj == null) {
            return value;
        }

        if (obj instanceof Integer) {
            value = (Integer) obj;
        } else {
            try {
                value = Integer.parseInt(obj.toString());
            } catch (Exception e) {
                value = 0;
            }
        }
        return value;
    }

    /**
     * 获取long类型值
     *
     * @param key key
     * @return long类型值
     */
    public long getLong(String key) {
        long value = 0;

        if (StringHelper.isEmpty(key)) {
            return value;
        }

        if (!containsKey(key)) {
            return value;
        }

        Object obj = this.get(key);
        if (obj == null) {
            return value;
        }
        if (obj instanceof Long) {
            value = (Long) obj;
        } else {
            try {
                value = Long.parseLong(obj.toString());
            } catch (Exception e) {
                value = 0;
            }
        }

        return value;
    }

    /**
     * 获取fload类型值
     *
     * @param key key
     * @return fload类型值
     */
    public float getFloat(String key) {
        float value = 0;

        if (StringHelper.isEmpty(key)) {
            return value;
        }

        if (!containsKey(key)) {
            return value;
        }

        Object obj = this.get(key);
        if (obj == null) {
            return value;
        }
        if (obj instanceof Long) {
            value = (Long) obj;
        } else {
            try {
                value = Float.parseFloat(obj.toString());
            } catch (Exception e) {
                value = 0;
            }
        }

        return value;
    }

    /**
     * 获取double类型值
     *
     * @param key key
     * @return double类型值
     */
    public double getDouble(String key) {
        double value = 0;

        if (StringHelper.isEmpty(key)) {
            return value;
        }

        if (!containsKey(key)) {
            return value;
        }

        Object obj = this.get(key);
        if (obj == null) {
            return value;
        }
        if (obj instanceof Long) {
            value = (Long) obj;
        } else {
            try {
                value = Double.parseDouble(obj.toString());
            } catch (Exception e) {
                value = 0;
            }
        }

        return value;
    }

    /**
     * 获取boolean类型值
     *
     * @param key key
     * @return boolean类型值
     */
    public boolean getBoolean(String key) {
        boolean value = false;

        if (StringHelper.isEmpty(key)) {
            return value;
        }

        if (!containsKey(key)) {
            return value;
        }

        Object obj = this.get(key);
        if (obj == null) {
            return value;
        }
        if (obj instanceof Boolean) {
            value = (Boolean) obj;
        } else {
            try {
                value = Boolean.parseBoolean(obj.toString());
            } catch (Exception e) {
                value = false;
            }
        }

        return value;
    }

    /**
     * 获取String类型值
     *
     * @param key key
     * @return String类型值
     */
    public String getString(String key) {
        if (StringHelper.isEmpty(key)) {
            return "";
        }

        Object obj = this.get(key);
        return obj == null ? "" : obj.toString();
    }

    /**
     * 获取Session内容
     *
     * @return HttpSession
     */
    public HttpSession getSession() {
        HttpSession obj = (HttpSession)this.get("_session");
        return obj;
    }
}
