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
 * @Description: elder_project
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_project")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_project")
public class ElderProject implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键，雪花ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
    private String id;
	/**租户ID（SaaS集团隔离）*/
	@Excel(name = "租户ID（SaaS集团隔离）", width = 15)
    @Schema(description = "租户ID（SaaS集团隔离）")
    private Integer tenantId;
	/**项目编码（如A001，用于账单号前缀）*/
	@Excel(name = "项目编码（如A001，用于账单号前缀）", width = 15)
    @Schema(description = "项目编码（如A001，用于账单号前缀）")
    private String projectCode;
	/**项目名称（如阳光养老社区）*/
	@Excel(name = "项目名称（如阳光养老社区）", width = 15)
    @Schema(description = "项目名称（如阳光养老社区）")
    private String projectName;
	/**类型：HIGH_END高端/STANDARD普惠/MEDICAL医养结合*/
	@Excel(name = "类型：HIGH_END高端/STANDARD普惠/MEDICAL医养结合", width = 15)
    @Schema(description = "类型：HIGH_END高端/STANDARD普惠/MEDICAL医养结合")
    private String projectType;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @Schema(description = "详细地址")
    private String address;
	/**状态：0 未启用 1 启用*/
	@Excel(name = "状态：0 未启用 1 启用", width = 15)
    @Schema(description = "状态：0 未启用 1 启用")
    private String status;
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
