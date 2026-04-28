package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: elder_customer
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderCustomerService extends IService<ElderCustomer> {

    Result<String> saveInfo(ElderCustomer elderCustomer);

    Result<String> updateInfo(ElderCustomer elderCustomer);

    IPage<ElderCustomer> pageList(Page<ElderCustomer> page, ElderCustomer elderCustomer);
}
