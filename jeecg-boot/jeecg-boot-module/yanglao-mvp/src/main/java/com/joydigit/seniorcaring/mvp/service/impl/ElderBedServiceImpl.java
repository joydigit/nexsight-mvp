package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBed;
import com.joydigit.seniorcaring.mvp.mapper.ElderBedMapper;
import com.joydigit.seniorcaring.mvp.service.IElderBedService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_bed
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderBedServiceImpl extends ServiceImpl<ElderBedMapper, ElderBed> implements IElderBedService {
    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderBed> pageList(Page<ElderBed> page, ElderBed elderBed) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderBed.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderBed);
    }
}
