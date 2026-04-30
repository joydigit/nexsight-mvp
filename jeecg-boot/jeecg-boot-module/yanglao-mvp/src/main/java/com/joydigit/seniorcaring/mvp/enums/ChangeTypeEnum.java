package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-30
 * @Description: TODO
 * @Version: 1.0
 */
public enum ChangeTypeEnum {
    CHECKIN("1","入住"),
    CHECKOUT("2","退住"),
    CHANGEROOM("3","换房"),
    ;

    private String key;
    private String msg;

    ChangeTypeEnum(String key, String msg) {
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
