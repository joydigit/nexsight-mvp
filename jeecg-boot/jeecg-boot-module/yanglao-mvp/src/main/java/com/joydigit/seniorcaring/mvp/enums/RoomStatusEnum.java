package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-30
 * @Description: TODO
 * @Version: 1.0
 */
public enum RoomStatusEnum {
    FREE("0","空闲"),
    RESERVED("1","预定"),
    OCCUPIED("2","入住"),
    MAINTENANCE("3","维修"),
    DEACTIVATE("4","停用"),
    ;

    private String key;
    private String msg;

    RoomStatusEnum(String key, String msg) {
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
