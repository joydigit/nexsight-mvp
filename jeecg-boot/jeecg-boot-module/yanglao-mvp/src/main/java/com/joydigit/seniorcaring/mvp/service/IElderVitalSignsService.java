package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderVitalSigns;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_vital_signs
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderVitalSignsService extends IService<ElderVitalSigns> {

    Result<String> saveInfo(ElderVitalSigns elderVitalSigns);

    IPage<ElderVitalSigns> pageList(Page<ElderVitalSigns> page, ElderVitalSigns elderVitalSigns);
}
