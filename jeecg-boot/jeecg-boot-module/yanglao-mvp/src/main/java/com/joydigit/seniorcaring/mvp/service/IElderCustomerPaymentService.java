package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerPayment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

import java.util.List;

/**
 * @Description: elder_customer_payment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderCustomerPaymentService extends IService<ElderCustomerPayment> {

    Result<String> saveInfo(ElderCustomerPayment elderCustomerPayment);

    Result<String> updateInfo(ElderCustomerPayment elderCustomerPayment);

    Result<String> removeInfo(String id);

    IPage<ElderCustomerPayment> pageList(Page<ElderCustomerPayment> page, ElderCustomerPayment elderCustomerPayment);

    List<ElderCustomerPayment> getList(ElderCustomerPayment elderCustomerPayment, List<String> selectionList);

    Result<String> confirm(String ids) throws Exception;
}
