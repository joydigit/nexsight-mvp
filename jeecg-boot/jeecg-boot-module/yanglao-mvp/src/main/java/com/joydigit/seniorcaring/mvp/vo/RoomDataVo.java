package com.joydigit.seniorcaring.mvp.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-27
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class RoomDataVo {
    private String roomId;
    private String area;
    private String roomNo;
    private String roomType;
    private String status;
    private List<BedStatusVo> beds;
}
