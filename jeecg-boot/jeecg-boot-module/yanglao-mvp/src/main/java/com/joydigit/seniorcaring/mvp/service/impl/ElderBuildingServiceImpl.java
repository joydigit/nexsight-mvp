package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBuilding;
import com.joydigit.seniorcaring.mvp.mapper.ElderBuildingMapper;
import com.joydigit.seniorcaring.mvp.service.IElderBuildingService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_building
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderBuildingServiceImpl extends ServiceImpl<ElderBuildingMapper, ElderBuilding> implements IElderBuildingService {
    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderBuilding> pageList(Page<ElderBuilding> page, ElderBuilding elderBuilding) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderBuilding.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderBuilding);
    }

    @Override
    public List<ElderBuilding> getBuildingListByProjectId(String projectId) {
        List<ElderBuilding> list = list(Wrappers.lambdaQuery(ElderBuilding.class).eq(ElderBuilding::getProjectId, projectId)
                .orderByDesc(ElderBuilding::getSortOrder));
        return list;
    }
}
