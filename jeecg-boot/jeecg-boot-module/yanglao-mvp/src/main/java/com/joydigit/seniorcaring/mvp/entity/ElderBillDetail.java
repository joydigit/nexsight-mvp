package com.joydigit.seniorcaring.mvp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

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
 * @Description: elder_bill_detail
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_bill_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_bill_detail")
public class ElderBillDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
    @Schema(description = "项目ID")
    private String projectId;
	/**账单ID*/
	@Excel(name = "账单ID", width = 15)
    @Schema(description = "账单ID")
    private String billId;
	/**费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他*/
	@Excel(name = "费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他", width = 15)
    @Schema(description = "费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他")
    private String itemType;
	/**费用名称（如"床位费-单人间"、"护理费-特级"）*/
	@Excel(name = "费用名称（如床位费-单人间、护理费-特级）", width = 15)
    @Schema(description = "费用名称（如床位费-单人间、护理费-特级)")
    private String itemName;
	/**数量（如15天，或1次）*/
	@Excel(name = "数量（如15天，或1次）", width = 15)
    @Schema(description = "数量（如15天，或1次）")
    private BigDecimal quantity;
	/**单价（元）*/
	@Excel(name = "单价（元）", width = 15)
    @Schema(description = "单价（元）")
    private BigDecimal unitPrice;
	/**金额（元）= quantity * unit_price*/
	@Excel(name = "金额（元）= quantity * unit_price", width = 15)
    @Schema(description = "金额（元）= quantity * unit_price")
    private BigDecimal amount;
	/**计算说明（如"入住15天，按天折算：6000/30*15"）*/
	@Excel(name = "计算说明（如入住15天，按天折算：6000/30*15）", width = 15)
    @Schema(description = "计算说明（如入住15天，按天折算：6000/30*15）")
    private String calcDesc;
	/**创建人*/
    @Schema(description = "创建人")
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
    private String billNo;
}
