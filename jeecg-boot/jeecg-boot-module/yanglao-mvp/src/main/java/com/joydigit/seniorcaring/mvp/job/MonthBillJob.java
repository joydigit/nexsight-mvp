package com.joydigit.seniorcaring.mvp.job;

import com.joydigit.seniorcaring.mvp.service.IElderBillService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        try {
            elderBillService.calMothBillList();
        } catch (Exception e){
            log.error("执行月费账单失败",e);
        }
    }
}
