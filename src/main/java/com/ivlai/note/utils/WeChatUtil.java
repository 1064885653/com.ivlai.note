package com.ivlai.note.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class WeChatUtil {

    /**
     * @param appid      小程序appid
     * @param appSecret  小程序开发者appSecret
     * @param code       小程序登录时获取的随机码
     * @param returnUtil 返回信息组装工具类
     */
    public static void getOpenid(String appid, String appSecret, String code, ReturnUtil returnUtil) {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
            String json = NetUtil.sendHttpRequestWithLineParameter(url);

            /* 将json字符串转换为map对象 */
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap hashMap = objectMapper.readValue(json, HashMap.class);
            Integer errcode = (Integer) hashMap.get("errcode");
            if (null == errcode) {
                /* 获取信息成功 */
                String openid = (String) hashMap.get("openid");
                if (null != openid) {
                    String session_key = (String) hashMap.get("session_key");
                    returnUtil.isSuccess("获取用户信息成功。");
                    returnUtil.add("openid", openid);
                    returnUtil.add("session_key", session_key);
                }else {
                    returnUtil.isFail("服务器异常，请稍后重试。");
                }
            } else if (-1 == errcode) {
                returnUtil.isFail("系统繁忙，请稍后重试。");
            } else if (40029 == errcode) {
                returnUtil.isFail("登录状态无效或已失效，请退出后重新登录。");
            } else if (45011 == errcode) {
                returnUtil.isFail("操作过于频繁，请稍后再试。");
            } else if (40163 == errcode) {
                returnUtil.isFail("登录凭证使用，请退出后重新登录。");
            } else {
                returnUtil.isFail("获取用户信息失败。");
            }
        } catch (IOException e) {
            returnUtil.isFail("从服务器获取信息失败。");
        }
    }

}
