package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.joydigit.seniorcaring.mvp.mapper.ElderConsultingMapper;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import com.joydigit.seniorcaring.mvp.vo.ElderConsultingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderConsultingServiceImpl extends ServiceImpl<ElderConsultingMapper, ElderConsulting> implements IElderConsultingService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderConsulting> pageList(Page<ElderConsulting> page,  ElderConsulting elderConsulting) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderConsulting.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderConsulting);
    }

    @Override
    public List<ElderConsulting> getList(ElderConsulting elderConsulting,List<String> selectionList) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderConsulting.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            return new ArrayList<>();
        }
        return this.baseMapper.getList(elderConsulting,selectionList);
    }

    @Override
    public List<ElderConsultingVo> getElderConsultingAllList(String projectId) {

        return this.baseMapper.getElderConsultingAllList(projectId);
    }
}
