package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_bed
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderBedMapper extends BaseMapper<ElderBed> {

    IPage<ElderBed> pageList(Page<ElderBed> page, @Param("data") ElderBed elderBed);
}
