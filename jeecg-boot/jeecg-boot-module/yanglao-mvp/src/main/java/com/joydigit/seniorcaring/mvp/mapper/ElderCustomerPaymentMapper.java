package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerPayment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_customer_payment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderCustomerPaymentMapper extends BaseMapper<ElderCustomerPayment> {

    IPage<ElderCustomerPayment> pageList(Page<ElderCustomerPayment> page,@Param("data") ElderCustomerPayment elderCustomerPayment);
    List<ElderCustomerPayment> getList(@Param("data") ElderCustomerPayment elderCustomerPayment,@Param("selectionList") List<String> selectionList);
}
