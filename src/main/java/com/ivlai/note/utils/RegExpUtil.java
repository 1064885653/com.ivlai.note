package com.ivlai.note.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式验证工具类
 */
public class RegExpUtil {

    /* 正则表达式集 */
    public static final String IS_PUBLIC_URL = "^http(s)?://.*/public/.*$";
    public static final String IS_URL = "^http(s)?://(www\\.)(.+)(\\..{1,7})$";
    public static final String IS_PUBLIC_URI = "^/public/.*$";
    public static final String IS_ADMIN = "^/admin.*$";
    public static final String IS_ADMIN_DO = "^/?admin/do?.*$";
    public static final String IS_ADMIN_DO_URL = "^http(s)?://.*/admin/do.*$";
    public static final String IS_FAVICON_ICO = "^.*/favicon.ico(n)?$";
    public static final String IS_MOBILE_PHONE_NUMBER = "^1[3-6][\\d]{9}$";
    public static final String IS_400_PHONE_NUMBER = "^400[\\d-]{7,9}$";
    public static final String IS_PHONE_NUMBER = "^[\\d]{3}[-]?[\\d]{8}$";
    public static final String IS_IMG = "^.*\\.(?i)(GIF|PNG|JPEG|JPG|APNG|WEBP)$";


    /**
     * 判断是否是URL地址
     *
     * @param url URL地址
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(url, IS_URL);
    }

    /**
     * 是否是一个请求公共组员的请求
     *
     * @param uri 请求
     * @return 验证结果
     */
    public static boolean isPublicUrl(String uri) {
        return Pattern.matches(IS_PUBLIC_URI, uri) || Pattern.matches(IS_PUBLIC_URL, uri) || Pattern.matches(IS_FAVICON_ICO, uri);
    }

    /**
     * 是否是管理员操作
     *
     * @param uri 请求
     * @return 验证结果
     */
    public static boolean isAdmin(String uri) {
        return Pattern.matches(IS_ADMIN, uri);
    }

    /**
     * 是否是管理员验证操作
     *
     * @param uri 请求
     * @return 验证结果
     */
    public static boolean isAdminDo(String uri) {
        return Pattern.matches(IS_ADMIN_DO, uri) || Pattern.matches(IS_ADMIN_DO_URL, uri);
    }

    /**
     * 图片格式验证
     *
     * @param str 需要验证的字符串
     * @return 验证结果
     */
    public static boolean isImg(String str) {
        return Pattern.matches(IS_IMG, str);
    }

    /**
     * 验证是否有匹配项
     *
     * @param string 验证数据
     * @param regExp 正则表达式
     * @return 验证结果
     */
    public static boolean matching(String string, String... regExp) {
        boolean status = false;
        for (String regExpLi : regExp) {
            if (Pattern.matches(regExpLi, string)) {
                status = true;
                break;
            }
        }
        return status;
    }

}
