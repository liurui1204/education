package com.mohe.nanjinghaiguaneducation.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author jay
 * @date 2020/2/4 10:42
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        if (data == null) {
            return null;
        } else {
            try {
                String string = MAPPER.writeValueAsString(data);
                return string;
            } catch (JsonProcessingException e) {
                logger.error("parse json string error", e);
                return null;
            }
        }
    }

    /**
     * json转map
     *
     * @param string
     * @return map
     */
    public static Map<String, Object> jsonToMap(String string) {
        return jsonToPojo(string,Map.class);
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        if (jsonData == null) {
            return null;
        } else {
            try {
                return MAPPER.readValue(jsonData, beanType);
            } catch (Exception e) {
                logger.error("parse string to object error", e);
                return null;
            }
        }
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        if (jsonData == null) {
            return Collections.emptyList();
        } else {

            try {
                JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
                return MAPPER.readValue(jsonData, javaType);
            } catch (Exception e) {
                logger.error("parse string to object error", e);
                return Collections.emptyList();
            }
        }
    }


}
