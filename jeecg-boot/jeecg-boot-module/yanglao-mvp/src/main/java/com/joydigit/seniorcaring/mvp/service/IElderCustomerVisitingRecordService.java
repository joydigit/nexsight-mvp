package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerVisitingRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_customer_visiting_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderCustomerVisitingRecordService extends IService<ElderCustomerVisitingRecord> {

    Result<String> saveInfo(ElderCustomerVisitingRecord elderCustomerVisitingRecord);

    IPage<ElderCustomerVisitingRecord> pageList(Page<ElderCustomerVisitingRecord> page, ElderCustomerVisitingRecord elderCustomerVisitingRecord);
}
