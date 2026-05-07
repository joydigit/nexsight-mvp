package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-06
 * @Description: TODO
 * @Version: 1.0
 */
public enum PaymentConfirmFlagEnum {
    NO("0","未确认"),
    YES("1","已确认"),
    ;

    private String key;
    private String msg;

    PaymentConfirmFlagEnum(String key, String msg) {
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
