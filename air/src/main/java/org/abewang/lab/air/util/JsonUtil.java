package org.abewang.lab.air.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author Abe
 * @Date 2018/8/17.
 */
public class JsonUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj){
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("obj转json失败", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T toPojo(String str, Class<T> classType) {
        try {
            return MAPPER.readValue(str, classType);
        } catch (IOException e) {
            LOGGER.error("json转obj失败", e);
            throw new RuntimeException(e);
        }
    }
}
