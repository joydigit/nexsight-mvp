package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderAssessment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_assessment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderAssessmentService extends IService<ElderAssessment> {

    IPage<ElderAssessment> pageList(Page<ElderAssessment> page, ElderAssessment elderAssessment);

    Result<String> saveInfo(ElderAssessment elderAssessment);
}
