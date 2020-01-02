package com.ivlai.note.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 字符串工具
 */
@SuppressWarnings({"unchecked"})
public class JsonUtil {

    /**
     * 从相对路径读取json并解析Map
     *
     * @param relativeFilePath 文件路径
     * @return json对应的map
     */
    public static HashMap<String, Object> relativeJsonFileToMap(String relativeFilePath) {
        InputStream resourceAsStream = FileUtil.class.getResourceAsStream(relativeFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        Object langObj;
        try {
            langObj = objectMapper.readValue(resourceAsStream, Object.class);
        } catch (IOException e) {
            throw new RuntimeException("Json File No Found Exception.");
        }
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Json File Close Exception.");
        }

        return (HashMap<String, Object>) langObj;
    }

    /**
     * json转换object
     */
    public Object jsonToObject(String json, Class c) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, c);
    }

    /**
     * object转json
     */
    public String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

}
