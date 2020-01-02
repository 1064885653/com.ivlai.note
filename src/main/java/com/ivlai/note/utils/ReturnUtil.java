package com.ivlai.note.utils;

import java.util.HashMap;

public class ReturnUtil {

    private String code;

    private String message;

    private HashMap<String, Object> data;

    public ReturnUtil() {
        data = new HashMap<>();
        code = "Fail";
    }

    /**
     * 操作成功
     *
     * @return 对象本身
     */
    public ReturnUtil isSuccess() {
        this.code = "Success";
        this.message = "request:ok";
        return this;
    }

    /**
     * 操作成功
     *
     * @param message 成功后需要反馈的信息
     * @return 对象本身
     */
    public ReturnUtil isSuccess(String message) {
        this.code = "Success";
        this.message = message;
        return this;
    }

    /**
     * 操作失败
     *
     * @return 对象本身
     */
    public ReturnUtil isFail() {
        this.code = "Fail";
        this.message = "request:ok;Operation failure.";
        return this;
    }

    /**
     * 操作失败
     *
     * @param message 错误信息
     * @return 对象本身
     */
    public ReturnUtil isFail(String message) {
        this.code = "Fail";
        this.message = message;
        return this;
    }

    /**
     * 添加一个元素
     *
     * @param key   返回数据集合key。
     * @param value 返回数据集合value。
     * @return 对象本身
     */
    public ReturnUtil add(String key, Object value) {
        this.code = "Success";
        this.data.put(key, value);
        return this;
    }

    /**
     * 将已有数据填充
     * @param data 一个需要完全添加的对象及合
     * @return 对象本身
     */
    public ReturnUtil addAll(HashMap<String, Object> data) {
        this.code = "Success";
        this.data.putAll(data);
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
