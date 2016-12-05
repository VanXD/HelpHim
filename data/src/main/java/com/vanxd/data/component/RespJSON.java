package com.vanxd.data.component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * JSON格式统一包装类。
 *
 * @param <T> 响应消息的result子类型
 */
@JsonPropertyOrder(alphabetic = false)
public class RespJSON<T> {

    /**
     * The code.
     */
    private int code;

    /**
     * The message.
     */
    private String message;

    /**
     * The result.
     */
    private T result;

    /**
     * Instantiates a new resp json.
     */
    public RespJSON() {
    }

    /**
     * 根据返回代码生成默认返回对象.
     *
     * @param respCode the resp code
     */
    public RespJSON(RespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMeaning();
    }

    /**
     * 生成自定义返回对象.
     *
     * @param code 错误代码
     * @param msg  错误消息
     */
    public RespJSON(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * 返回指定相应码
     *
     * @param respCode
     * @return
     */
    public static RespJSON respCode(RespCode respCode) {
        return new RespJSON(respCode);
    }

    /**
     * 返回成功
     * @return
     */
    public static RespJSON success() {
        return new RespJSON(RespCode.SUCCESS);
    }

    /**
     * 返回成功，且返回数据
     *
     * @param object    成功返回的对象
     * @return
     */
    public static RespJSON successData(Object object) {
        RespJSON<Object> respJSON = new RespJSON(RespCode.SUCCESS);
        respJSON.setResult(object);
        return respJSON;
    }

    /**
     * 返回异常错误码和异常信息
     *
     * @param exception 异常对象
     * @return
     */
    public static RespJSON exception(Object exception) {
        RespJSON<Object> respJSON = new RespJSON(RespCode.EXCEPTION);
        respJSON.setResult(exception);
        return respJSON;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code the new code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result the new result
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    public enum RespCode {

        SUCCESS(200, "操作成功"),

        FAIL(0, "操作失败"),

        DATA_EMPTY(-1, "未获取到数据"),

        PARAM_ILLEAGUE(-2, "参数不合法"),

        EXCEPTION(-3, "发生异常"),;

        /**
         * 响应代码.
         */
        private int code;

        /**
         * 响应含义.
         */
        private String meaning;

        /**
         * Instantiates a new resp code.
         *
         * @param code    the code
         * @param meaning the meaning
         */
        private RespCode(int code, String meaning) {
            this.code = code;
            this.meaning = meaning;
        }

        /**
         * 获取对应的响应代码.
         *
         * @return the 响应代码
         */
        public int getCode() {
            return this.code;
        }

        /**
         * 获取对应的响应代码含义.
         *
         * @return the 响应含义
         */
        public String getMeaning() {
            return this.meaning;
        }
    }

}