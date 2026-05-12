package com.joydigit.seniorcaring.mvp.vo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-27
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class BedStatusVo {
    private String bedId;
    private String bedNo;
    private String status;
    private String roomId;
    private BigDecimal price;
    private BigDecimal dprice;
    private String customerId;
    private String customerName;

}
