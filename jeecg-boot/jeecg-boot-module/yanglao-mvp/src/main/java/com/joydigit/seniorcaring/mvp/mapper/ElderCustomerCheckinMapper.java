package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    int checkBedStatusByBedId(@Param("customerId") String customerId, @Param("bedId") String bedId);

    IPage<ElderCustomerCheckin> pageList(Page<ElderCustomerCheckin> page,@Param("data") ElderCustomerCheckin elderCustomerCheckin);
    @InterceptorIgnore(tenantLine = "true")
    List<Integer> getTenantIdList();
}
