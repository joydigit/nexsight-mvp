package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderConsultingMapper extends BaseMapper<ElderConsulting> {

    IPage<ElderConsulting> pageList(Page<ElderConsulting> page, @Param("elderConsulting") ElderConsulting elderConsulting);
}
