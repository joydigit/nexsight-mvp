package com.joydigit.seniorcaring.mvp.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-23
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class RoomCascaderVo {

    private String value;
    private String label;
    private List<RoomCascaderVo> children;
}
