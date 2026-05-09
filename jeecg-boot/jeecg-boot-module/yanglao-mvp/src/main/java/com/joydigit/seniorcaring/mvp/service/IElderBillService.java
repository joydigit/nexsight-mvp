package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBill;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

import java.util.List;

/**
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderBillService extends IService<ElderBill> {

    IPage<ElderBill> pageList(Page<ElderBill> page, ElderBill elderBill);

    Result<String> saveInfo(ElderBill elderBill);

    Result<String> updateInfo(ElderBill elderBill);

    Result<String> deleteInfo(String id);

    Result<String> deleteInfos(List<String> list);

    Result<List<ElderBill>> getBillListByCustomerId(String customerId);
}
