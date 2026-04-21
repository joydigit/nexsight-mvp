package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.joydigit.seniorcaring.mvp.mapper.ElderConsultingMapper;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderConsultingServiceImpl extends ServiceImpl<ElderConsultingMapper, ElderConsulting> implements IElderConsultingService {

    @Override
    public IPage<ElderConsulting> pageList(Page<ElderConsulting> page,  ElderConsulting elderConsulting) {
        return this.baseMapper.pageList(page,elderConsulting);
    }
}
