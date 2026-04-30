package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_customer_checkin
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderCustomerCheckinService extends IService<ElderCustomerCheckin> {

    IPage<ElderCustomerCheckin> pageList(Page<ElderCustomerCheckin> page, ElderCustomerCheckin elderCustomerCheckin);

    Result<String> saveInfo(ElderCustomerCheckin elderCustomerCheckin);

    Result<String> updateInfo(ElderCustomerCheckin elderCustomerCheckin);
}
