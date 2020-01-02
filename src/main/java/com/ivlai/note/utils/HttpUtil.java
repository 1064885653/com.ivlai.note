package com.ivlai.note.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpUtil {

    public static Map getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        return parameterMap;
    }

}
