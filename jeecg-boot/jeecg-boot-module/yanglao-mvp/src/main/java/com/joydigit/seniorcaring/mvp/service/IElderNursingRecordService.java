package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderNursingRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_nursing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderNursingRecordService extends IService<ElderNursingRecord> {

    IPage<ElderNursingRecord> pageList(Page<ElderNursingRecord> page, ElderNursingRecord elderNursingRecord);

    Result<String> saveInfo(ElderNursingRecord elderNursingRecord);
}
