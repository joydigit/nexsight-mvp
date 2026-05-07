package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-06
 * @Description: TODO
 * @Version: 1.0
 */
public enum TransactionTypeEnum {
    RECHARGE("1","充值"),
    CONSUMPTION("2","消费"),
    REFUND("3","退款"),
    PAY_FEES("4","缴费"),
    ;

    private String key;
    private String msg;

    TransactionTypeEnum(String key, String msg) {
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
