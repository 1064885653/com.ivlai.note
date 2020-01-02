package com.ivlai.note.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 网络工具类
 */
public class NetUtil {

    /**
     * @param strUrl 字符串类型URL请求
     */
    public static String urlSendAndGetStringFile(String strUrl) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(strUrl).openStream()));
        String temp;
        StringBuilder reStringBuilder = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null) {
            reStringBuilder.append(temp).append("\n");
        }
        return reStringBuilder.toString();
    }

    /**
     * 读取请求的网页信息并保存
     *
     * @param ulr  请求地址
     * @param file 保存的文件位置
     */
    public static void saveHtmlByUrl(String ulr, File file) throws IOException {
        String s = urlSendAndGetStringFile(ulr);
        FileUtil.saveFileByString(s, file);
    }

    /**
     * 获取 真实的IP地址
     * <p>
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 发送get请求的http请求,参数在map集合里
     *
     * @param strUrl 字符串类型的请求地址，不带参数
     * @param map    参数map集合
     * @return 返回数据
     */
    public static String sendHttpRequestWithMapParameter(String strUrl, HashMap<String, String> map) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(strUrl);
        /* 判断是否已经有部分参数了 */
        if (stringBuilder.indexOf("?") != -1) {
            stringBuilder.append("?");
        } else {
            stringBuilder.append("&");
        }
        for (String key : map.keySet()) {
            String parameter = key + "=" + map.get(key) + "&";
            stringBuilder.append(parameter);
        }
        return sendHttpRequestWithLineParameter(stringBuilder.toString());
    }

    /**
     * 发送get请求的http请求,带参数
     *
     * @param strUrl 字符串类型的请求地址,带参数
     * @return 返回数据
     */
    public static String sendHttpRequestWithLineParameter(String strUrl) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(strUrl).openStream()));
        String temp;
        StringBuilder reStringBuilder = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null) {
            reStringBuilder.append(temp).append("\n");
        }
        return reStringBuilder.toString();
    }

    /**
     * 将URL中的中文转码
     *
     * @param url url
     */
    public static String cnUrlEncoding(String url) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            char a = url.charAt(i);
            if (a > 127) {//将中文UTF-8编码
                sb.append(URLEncoder.encode(String.valueOf(a), "utf-8"));
            } else {
                sb.append(String.valueOf(a));
            }
        }
        return sb.toString();
    }
}
