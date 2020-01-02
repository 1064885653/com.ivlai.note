package com.ivlai.note.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 国际化工具类
 */
public class I18nUtil {

    /**
     * 保存语言信息到Session
     */
    public static void saveI18nToSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String lang = "zh_CN";
        Map langMap;

        String langP = request.getParameter("lang");
        if (null != langP && langP.length() > 0) {
            /* 从参数获取Lang信息 - 切换语言*/
            lang = langP;
            saveLangMapToSession(session, lang);
            TimeUtil.WriteTimeLog("注入新的语言包信息成功");
        } else {
            /* 查询本地langMap信息 */
            langMap = (Map) session.getAttribute("langMap");
            if (null == langMap || langMap.isEmpty()) {
                /* 本地未储存langMap信息 - 获取本地储存的lang信息 */
                String langS = (String) session.getAttribute("lang");
                if (null != langS && langS.length() > 0) {
                    /* 本地储存着lang信息 - 使用本地储存的lang信息 */
                    lang = langS;
                }
                /* 查询该语言包下的语言信息 */
                saveLangMapToSession(session, lang);
                TimeUtil.WriteTimeLog("注入新的语言包信息成功");
            }
        }
    }

    /**
     * langMap保存到session
     */
    private static void saveLangMapToSession(HttpSession session, String langName) {
        InputStream resourceAsStream = I18nUtil.class.getResourceAsStream("/i18n/i18n_" + langName + ".properties");
        if (null == resourceAsStream) {
            throw new RuntimeException("I18n File No Found Exception. Unsupported Language.");
        }

        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("I18n File No Found Exception.Unsupported Language");
        }
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        session.setAttribute("lang", langName);
        session.setAttribute("langMap", (Map) properties);
    }

    public static void saveI18nToSessionByJson(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String lang = "zh_CN";
        Object langObj;

        String langP = request.getParameter("lang");
        if (null != langP && langP.length() > 0) {
            /* 从参数获取Lang信息 - 切换语言*/
            lang = langP;
            saveLangObjectToSession(session, lang);
            TimeUtil.WriteTimeLog("注入新的语言包信息成功");
        } else {
            /* 查询本地langMap信息 */
            langObj = (Map) session.getAttribute("langObj");
            if (null == langObj || langObj.toString().isEmpty()) {
                /* 本地未储存langMap信息 - 获取本地储存的lang信息 */
                String langS = (String) session.getAttribute("lang");
                if (null != langS && langS.length() > 0) {
                    /* 本地储存着lang信息 - 使用本地储存的lang信息 */
                    lang = langS;
                }
                /* 查询该语言包下的语言信息 */
                saveLangObjectToSession(session, lang);
                TimeUtil.WriteTimeLog("注入新的语言包信息成功");
            }
        }
    }

    /**
     * saveLangObjectToSession
     */
    private static void saveLangObjectToSession(HttpSession session, String lang) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream resourceAsStream = I18nUtil.class.getResourceAsStream("/i18n/i18n_" + lang + ".json");
        Object langObj;
        try {
            langObj = objectMapper.readValue(resourceAsStream, Object.class);
        } catch (IOException e) {
            throw new RuntimeException("I18n File No Found Exception.Unsupported Language");
        }
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            throw new RuntimeException("I18n File Close Exception.Unsupported Language");
        }
        session.setAttribute("lang", lang);
        session.setAttribute("langObj", langObj);
    }


    /**
     * 从session中获取lang
     */
    public static String getLangForSession(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("lang");
    }

}
