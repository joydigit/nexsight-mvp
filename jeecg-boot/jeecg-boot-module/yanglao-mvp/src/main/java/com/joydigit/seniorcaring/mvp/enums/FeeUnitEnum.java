package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-11
 * @Description: TODO
 * @Version: 1.0
 */
public enum FeeUnitEnum {
    TIMES("1","次"),
    DAY("2","日"),
    MONTH("3","月"),
    ;

    private String key;
    private String msg;

    FeeUnitEnum(String key, String msg) {
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
