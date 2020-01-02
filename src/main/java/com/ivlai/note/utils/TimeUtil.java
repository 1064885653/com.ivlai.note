package com.ivlai.note.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取格式化后的时间
     *
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String getDateByFormat(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取默认格式的日期
     */
    public static String getDefaultDate() {
        return getDateByFormat("yyyy-MM-dd");
    }

    /**
     * 获取默认格式时间
     */
    public static String getDefaultTime() {
        return getDateByFormat("HH:mm:ss");
    }


    /**
     * 打印自定义时间格式的日志信息
     *
     * @return 打印的日志信息
     */
    public static String WriteTimeLog(String timeFormat, String log) {
        String date = new SimpleDateFormat(timeFormat).format(new Date());
        String logInfo = "[" + date + "] " + log;
        System.out.println(logInfo);
        return logInfo;
    }

    /**
     * 打印 yyyy-MM-dd HH:mm:ss 时间格式的日志信息
     *
     * @return 打印的日志信息
     */
    public static String WriteTimeLog(String log) {
        return WriteTimeLog("yyyy-MM-dd HH:mm:ss", log);
    }


}
