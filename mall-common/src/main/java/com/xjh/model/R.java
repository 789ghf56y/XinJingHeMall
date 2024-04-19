package com.xjh.model;

import com.xjh.status.StatusCode;

import java.io.Serializable;

public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = StatusCode.SIMPLE_SUCCESS.getCode();//成功
    private int code;
    private String msg;
    private T data;

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R(int code) {
        this.code = code;
    }

    public static <T>R<T> ok(){
        return new R<T>(SUCCESS,null,null);
    }

    public static <T>R<T> ok(T data){
        return new R<T>(SUCCESS,null,data);
    }

    public static <T>R<T> ok(T data,String msg){
        return new R<T>(SUCCESS,msg,data);
    }

    public static <T>R<T> fail(){
        return new R<T>(StatusCode.SIMPLE_FAIL.getCode(),null,null);
    }

    public static <T>R<T> fail(T data){
        return new R<T>(StatusCode.SIMPLE_FAIL.getCode(),null,data);
    }

    public static <T>R<T> fail(T data,String msg){
        return new R<T>(StatusCode.SIMPLE_FAIL.getCode(),msg,data);
    }

    public static <T>R<T> fail(int code,String msg){
        return new R<T>(code,msg,null);
    }

    public static <T>R<T> fail(int code,T data,String msg){
        return new R<T>(code,msg,data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
