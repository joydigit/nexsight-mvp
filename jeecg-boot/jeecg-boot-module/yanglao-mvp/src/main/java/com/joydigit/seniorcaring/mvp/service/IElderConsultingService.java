package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderConsultingService extends IService<ElderConsulting> {

    IPage<ElderConsulting> pageList(Page<ElderConsulting> page, ElderConsulting elderConsulting);
    List<ElderConsulting> getList(ElderConsulting elderConsulting,List<String> selectionList);
}
