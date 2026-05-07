package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-07
 * @Description: TODO
 * @Version: 1.0
 */
public enum BillStatusEnum {
    UNPAYMENT("0","未收款"),
    PARTIAL_PAYMENT("1","部分收款"),
    SETTLED("2","已结清"),
    OBSOLETE("3","已作废"),
    ;

    private String key;
    private String msg;

    BillStatusEnum(String key, String msg) {
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
