package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderFloor;
import com.joydigit.seniorcaring.mvp.mapper.ElderFloorMapper;
import com.joydigit.seniorcaring.mvp.service.IElderFloorService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_floor
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderFloorServiceImpl extends ServiceImpl<ElderFloorMapper, ElderFloor> implements IElderFloorService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderFloor> pageList(Page<ElderFloor> page, ElderFloor elderFloor) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderFloor.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderFloor);
    }

    @Override
    public List<ElderFloor> getFloorListByBuildingId(String buildingId) {
        List<ElderFloor> list = list(Wrappers.lambdaQuery(ElderFloor.class).eq(ElderFloor::getBuildingId, buildingId).orderByDesc(ElderFloor::getSortOrder));
        return list;
    }
}
