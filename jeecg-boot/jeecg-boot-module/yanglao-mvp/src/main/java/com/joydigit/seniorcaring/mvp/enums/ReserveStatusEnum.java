package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-12
 * @Description: TODO
 * @Version: 1.0
 */
public enum ReserveStatusEnum {
    RESERVE("1","预定"),
    UNRESERVE("2","取消预定"),
    ;

    private String key;
    private String msg;

    ReserveStatusEnum(String key, String msg) {
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
