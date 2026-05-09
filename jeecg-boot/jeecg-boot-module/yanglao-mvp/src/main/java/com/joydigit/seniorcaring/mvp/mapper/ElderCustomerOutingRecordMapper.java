package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerOutingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_customer_outing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderCustomerOutingRecordMapper extends BaseMapper<ElderCustomerOutingRecord> {

    IPage<ElderCustomerOutingRecord> pageList(Page<ElderCustomerOutingRecord> page,@Param("data") ElderCustomerOutingRecord elderCustomerOutingRecord);
}
