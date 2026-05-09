package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderBillDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_bill_detail
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderBillDetailMapper extends BaseMapper<ElderBillDetail> {

    IPage<ElderBillDetail> pageList(Page<ElderBillDetail> page,@Param("data") ElderBillDetail elderBillDetail);
}
