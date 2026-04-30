package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-30
 * @Description: TODO
 * @Version: 1.0
 */
public enum StatusEnum {
    NO("0","失效"),
    YES("1","有效"),
    ;

    private String key;
    private String msg;

    StatusEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }
}
