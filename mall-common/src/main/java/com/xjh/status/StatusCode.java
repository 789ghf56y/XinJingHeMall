package com.xjh.status;

public enum StatusCode {
    SIMPLE_SUCCESS(200,"请求成功"),
    SIMPLE_FAIL(500,"请求失败"),
    LOGIN_TIMEOUT(10002,"登录超时"),
    USER_UNAUTHORIZED(10050,"认证失败，TOKEN不合法"),

    USER_PARAMETER(10051,"参数不合法，参数异常"),

    USER_LOGIN(10052,"登录失败"),
    ;


    private final int code;
    private final String description;

    StatusCode(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Constants{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
