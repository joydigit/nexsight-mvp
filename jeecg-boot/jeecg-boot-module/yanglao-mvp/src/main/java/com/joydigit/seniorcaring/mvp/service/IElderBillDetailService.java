package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBillDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: elder_bill_detail
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderBillDetailService extends IService<ElderBillDetail> {

    IPage<ElderBillDetail> pageList(Page<ElderBillDetail> page, ElderBillDetail elderBillDetail);
}
