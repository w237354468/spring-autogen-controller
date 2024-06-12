package org.lcdpframework.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.warn("error occurred when object convert to json string");
        }
        return null;
    }

    public static <T> T readJson(String json, Class<T> cClass) {
        try {
            return objectMapper.readValue(json, cClass);
        } catch (JsonProcessingException e) {
            logger.warn("error occurred when json string convert to object");
        }
        return null;
    }
}
