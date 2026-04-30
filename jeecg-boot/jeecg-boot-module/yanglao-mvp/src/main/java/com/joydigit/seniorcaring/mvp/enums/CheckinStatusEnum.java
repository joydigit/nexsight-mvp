package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-30
 * @Description: TODO
 * @Version: 1.0
 */
public enum CheckinStatusEnum {
    CHECKIN("1","入住"),
    CHECKOUT("2","退住");

    private String key;
    private String msg;

    CheckinStatusEnum(String key, String msg) {
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
