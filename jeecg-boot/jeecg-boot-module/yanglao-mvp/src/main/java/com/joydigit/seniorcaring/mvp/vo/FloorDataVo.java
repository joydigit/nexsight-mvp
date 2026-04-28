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
public class FloorDataVo {
    private String projectId;
    private String projectName;
    private String floorId;
    private String floorName;
    private String buildingId;
    private String buildingName;
    private List<RoomDataVo> rooms;
}
