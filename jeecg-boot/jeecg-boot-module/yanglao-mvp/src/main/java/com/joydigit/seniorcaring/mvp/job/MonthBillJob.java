package com.joydigit.seniorcaring.mvp.job;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.joydigit.seniorcaring.mvp.service.IElderBillService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-11
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Component
public class MonthBillJob implements Job {
    @Autowired
    private IElderBillService elderBillService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 计划是每月初一凌晨定时执行，前一天计算上个月
        Date calDate = DateUtil.offsetDay(new Date(),-1);
        log.info("{} 开始执行月费账单计算",DateUtil.format(calDate, DatePattern.NORM_DATE_PATTERN));
        try {
            elderBillService.calMothBillList(calDate);
        } catch (Exception e){
            log.error("执行月费账单失败",e);
        }
    }

}
