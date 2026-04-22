package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.entity.ElderFloor;

import java.util.List;

/**
 * @Description: elder_floor
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderFloorService extends IService<ElderFloor> {

    IPage<ElderFloor> pageList(Page<ElderFloor> page, ElderFloor elderFloor);
    List<ElderFloor> getFloorListByBuildingId(String buildingId);
}
