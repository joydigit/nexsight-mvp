package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderConsultingFollowRecord;
import com.joydigit.seniorcaring.mvp.mapper.ElderConsultingFollowRecordMapper;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingFollowRecordService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: elder_consulting_follow_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderConsultingFollowRecordServiceImpl extends ServiceImpl<ElderConsultingFollowRecordMapper, ElderConsultingFollowRecord> implements IElderConsultingFollowRecordService {

    @Override
    public IPage<ElderConsultingFollowRecord> pageList(Page<ElderConsultingFollowRecord> page, ElderConsultingFollowRecord elderConsultingFollowRecord) {
        return this.baseMapper.pageList(page,elderConsultingFollowRecord);
    }
}
