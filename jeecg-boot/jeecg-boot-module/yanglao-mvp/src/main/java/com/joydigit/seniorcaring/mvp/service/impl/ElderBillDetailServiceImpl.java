package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBillDetail;
import com.joydigit.seniorcaring.mvp.mapper.ElderBillDetailMapper;
import com.joydigit.seniorcaring.mvp.service.IElderBillDetailService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: elder_bill_detail
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderBillDetailServiceImpl extends ServiceImpl<ElderBillDetailMapper, ElderBillDetail> implements IElderBillDetailService {

    @Override
    public IPage<ElderBillDetail> pageList(Page<ElderBillDetail> page, ElderBillDetail elderBillDetail) {
        return this.baseMapper.pageList(page,elderBillDetail);
    }
}
