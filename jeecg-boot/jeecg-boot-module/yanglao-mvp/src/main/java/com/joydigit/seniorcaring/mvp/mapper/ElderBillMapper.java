package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderBillMapper extends BaseMapper<ElderBill> {

    IPage<ElderBill> pageList(Page<ElderBill> page, @Param("data") ElderBill elderBill);
}
