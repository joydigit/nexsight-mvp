package com.joydigit.seniorcaring.mvp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @Description: elder_customer_checkin
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_customer_checkin")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_customer_checkin")
public class ElderCustomerCheckin implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**所属项目ID*/
	@Excel(name = "所属项目ID", width = 15)
    @Schema(description = "所属项目ID")
    private String projectId;
	/**销售名称*/
	@Excel(name = "销售名称", width = 15)
    @Schema(description = "销售名称")
    private String salesName;
	/**咨询接待id*/
	@Excel(name = "咨询接待id", width = 15)
    @Schema(description = "咨询接待id")
    private String consultingId;
	/**销售id*/
	@Excel(name = "销售id", width = 15)
    @Schema(description = "销售id")
    private String salesId;
	/**入住时间*/
	@Excel(name = "入住时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "入住时间")
    private Date checkinTime;
	/**合同url*/
	@Excel(name = "合同url", width = 15)
    @Schema(description = "合同url")
    private String contractFileUrl;
	/**合同号*/
	@Excel(name = "合同号", width = 15)
    @Schema(description = "合同号")
    private String contractNo;
	/**实际退住时间*/
	@Excel(name = "实际退住时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "实际退住时间")
    private Date realCheckoutTime;
	/**预计退住时间*/
	@Excel(name = "预计退住时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "预计退住时间")
    private Date expectCheckoutTime;
	/**入住类型 1长住 2短住*/
	@Excel(name = "入住类型 1长住 2短住", width = 15)
    @Schema(description = "入住类型 1长住 2短住")
    private String checkinType;
	/**状态 1 入住 2 退住*/
	@Excel(name = "状态 1 入住 2 退住", width = 15)
    @Schema(description = "状态 1 入住 2 退住")
    private String status;
	/**客户id*/
	@Excel(name = "客户id", width = 15)
    @Schema(description = "客户id")
    private String customerId;
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
}
