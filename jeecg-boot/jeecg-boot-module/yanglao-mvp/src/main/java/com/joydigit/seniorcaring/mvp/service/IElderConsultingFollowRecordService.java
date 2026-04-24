package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.entity.ElderConsultingFollowRecord;

/**
 * @Description: elder_consulting_follow_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderConsultingFollowRecordService extends IService<ElderConsultingFollowRecord> {

    IPage<ElderConsultingFollowRecord> pageList(Page<ElderConsultingFollowRecord> page, ElderConsultingFollowRecord elderConsultingFollowRecord);
}
