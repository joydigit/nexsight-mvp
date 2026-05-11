package com.joydigit.seniorcaring.mvp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_bill")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_bill")
public class ElderBill implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键，雪花ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
    private String id;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**项目ID（逻辑外键）*/
	@Excel(name = "项目ID（逻辑外键）", width = 15)
    @Schema(description = "项目ID（逻辑外键）")
    private String projectId;
	/**账单号（唯一，如A001202604001）*/
	@Excel(name = "账单号（唯一，如A001202604001）", width = 15)
    @Schema(description = "账单号（唯一，如A001202604001）")
    private String billNo;
	/**客户ID（逻辑外键）*/
	@Excel(name = "客户ID（逻辑外键）", width = 15)
    @Schema(description = "客户ID（逻辑外键）")
    private String customerId;
	/**入住id*/
	@Excel(name = "入住id", width = 15)
    @Schema(description = "入住id")
    private String checkinId;
	/**账单月份（yyyy-MM，如2026-04）*/
	@Excel(name = "账单月份（yyyy-MM，如2026-04）", width = 15)
    @Schema(description = "账单月份（yyyy-MM，如2026-04）")
    private String billMonth;
	/**应收总额（元）*/
	@Excel(name = "应收总额（元）", width = 15)
    @Schema(description = "应收总额（元）")
    private BigDecimal totalAmount;
	/**实收总额（元）*/
	@Excel(name = "实收总额（元）", width = 15)
    @Schema(description = "实收总额（元）")
    private BigDecimal paidAmount;
	@Excel(name = "未交金额（元）", width = 15)
    @Schema(description = "未交金额（元）")
    private BigDecimal unpaidAmount;


	/**优惠/减免金额（元）*/
	@Excel(name = "优惠/减免金额（元）", width = 15)
    @Schema(description = "优惠/减免金额（元）")
    private BigDecimal discountAmount;
	/**账单状态：0-未收款，1-部分收款，2-已结清，3-已作废*/
	@Excel(name = "账单状态：0-未收款，1-部分收款，2-已结清，3-已作废", width = 15)
    @Schema(description = "账单状态：0-未收款，1-部分收款，2-已结清，3-已作废")
    private String status;
	/**应收日期（如每月5日）*/
	@Excel(name = "应收日期（如每月5日）", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "应收日期（如每月5日）")
    private Date dueDate;
	/**备注（如"首月按天折算"）*/
	@Excel(name = "备注（如首月按天折算）", width = 15)
    @Schema(description = "备注（如首月按天折算）")
    private String remark;
	/**创建人（系统生成或操作人）*/
    @Schema(description = "创建人（系统生成或操作人）")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private Date updateTime;
	/**删除状态：0正常，1已删除*/
	@Excel(name = "删除状态：0正常，1已删除", width = 15)
    @Schema(description = "删除状态：0正常，1已删除")
    @TableLogic
    private Integer delFlag;

    @TableField(exist = false)
    @Excel(name = "客户", width = 15,orderNum = "1")
    private String customerName;
    @Excel(name = "项目", width = 15,orderNum = "0")
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private List<String> projectIds;
}
