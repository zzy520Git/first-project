package com.firstproject.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2018/7/31 11:04
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJSONString(Object object)
    {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.error("解析:{} json出错:{}",object,e);
        }
        return null;
    }
    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            logger.error("解析字符串:{} json出错:{}",text,e);
        }
        return null;
    }

    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            return JSON.parseArray(jsonStr,clazz);
        } catch (Exception e) {
            logger.error("解析字符串:{} json出错:{}",jsonStr,e);
        }
        return null;
    }

    public static JSONObject parseObject(String jsonText) {
        try {
            return JSON.parseObject(jsonText);
        } catch (Exception e) {
            logger.error("解析字符串:{} json出错:{}",jsonText,e);
        }
        return null;
    }
    public static JSONArray parseArray(String jsonStr) {
        try {
            return JSON.parseArray(jsonStr);
        } catch (Exception e) {
            logger.error("解析字符串:{} json出错:{}",jsonStr,e);
        }
        return null;
    }
}
