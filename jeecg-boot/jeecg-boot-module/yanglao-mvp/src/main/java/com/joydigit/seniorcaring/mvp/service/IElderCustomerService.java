package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.vo.CustomerSelectVo;
import com.joydigit.seniorcaring.mvp.vo.ElderCustomerVo;
import org.jeecg.common.api.vo.Result;

import java.util.List;

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

    Result<ElderCustomerVo> getCustomerLastInfo(String id);

    Result<List<CustomerSelectVo>> getCustomerListByProjectId(String projectId);
}
