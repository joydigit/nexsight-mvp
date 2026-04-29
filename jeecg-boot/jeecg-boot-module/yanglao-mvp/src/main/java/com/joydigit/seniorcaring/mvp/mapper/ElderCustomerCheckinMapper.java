package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_customer_checkin
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderCustomerCheckinMapper extends BaseMapper<ElderCustomerCheckin> {

    ElderCustomerCheckin getLastInfoByCustomerId(@Param("customerId") String customerId);
}
