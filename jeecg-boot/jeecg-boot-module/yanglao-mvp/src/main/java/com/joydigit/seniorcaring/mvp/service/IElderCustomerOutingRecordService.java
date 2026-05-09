package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerOutingRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_customer_outing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderCustomerOutingRecordService extends IService<ElderCustomerOutingRecord> {

    Result<String> saveInfo(ElderCustomerOutingRecord elderCustomerOutingRecord);

    IPage<ElderCustomerOutingRecord> pageList(Page<ElderCustomerOutingRecord> page, ElderCustomerOutingRecord elderCustomerOutingRecord);
}
