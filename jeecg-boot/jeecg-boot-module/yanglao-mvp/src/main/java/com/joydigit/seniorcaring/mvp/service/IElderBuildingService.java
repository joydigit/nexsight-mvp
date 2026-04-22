package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.entity.ElderBuilding;

import java.util.List;

/**
 * @Description: elder_building
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderBuildingService extends IService<ElderBuilding> {

    IPage<ElderBuilding> pageList(Page<ElderBuilding> page, ElderBuilding elderBuilding);


    List<ElderBuilding> getBuildingListByProjectId(String projectId);
}
