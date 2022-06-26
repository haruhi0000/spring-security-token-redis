package com.haruhi.security.web;

/**
 * 接口返回参数
 * @author 61711
 * @param <T>
 */
public class ApiResult<T> {
    private int code;
    private String msg;
    private long count;
    private T data;

    /**
     *
     * @param code
     * @param msg
     * @param count
     * @param data
     */
    public ApiResult(int code, String msg, long count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult() {

    }

    public static <T> ApiResult<T> error(int code, String msg) {
        return new ApiResult<T>(code,msg);
    }
    public static <T> ApiResult<T> success() {
        return new ApiResult<T>(200,"success",0,null);
    }
    public static <T> ApiResult<T> success(int code, String msg, long count, T data) {
        return new ApiResult<T>(code,msg,count,data);
    }
    public static <T> ApiResult<T> success(int code, String msg) {
        return new ApiResult<T>(code,msg);
    }

    public static <T> ApiResult<T> success(T t) {
        return new ApiResult<T>(200,"success",0, t);
    }

    public static <T> ApiResult<T> failed(String msg) {
        return new ApiResult<T>(500, msg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
