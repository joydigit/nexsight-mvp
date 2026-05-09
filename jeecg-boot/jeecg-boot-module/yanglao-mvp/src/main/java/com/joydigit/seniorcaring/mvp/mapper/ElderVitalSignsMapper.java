package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderVitalSigns;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_vital_signs
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderVitalSignsMapper extends BaseMapper<ElderVitalSigns> {

    IPage<ElderVitalSigns> pageList(Page<ElderVitalSigns> page,@Param("data") ElderVitalSigns elderVitalSigns);
}
