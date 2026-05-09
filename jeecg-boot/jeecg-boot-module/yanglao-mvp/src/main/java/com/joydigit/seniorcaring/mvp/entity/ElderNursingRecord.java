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
 * @Description: elder_nursing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_nursing_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_nursing_record")
public class ElderNursingRecord implements Serializable {
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
	/**客户ID*/
	@Excel(name = "客户ID", width = 15)
    @Schema(description = "客户ID")
    private String customerId;
	/**入住id*/
	@Excel(name = "入住id", width = 15)
    @Schema(description = "入住id")
    private String checkinId;
	/**护理时间*/
	@Excel(name = "护理时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "护理时间")
    private Date nursingDate;
	/**护理项目编码（如ORAL_CARE口腔护理）*/
	@Excel(name = "护理项目编码（如ORAL_CARE口腔护理）", width = 15)
    @Schema(description = "护理项目编码（如ORAL_CARE口腔护理）")
    private String itemCode;
	/**执行人ID（user_id逻辑外键）*/
	@Excel(name = "执行人ID（user_id逻辑外键）", width = 15)
    @Schema(description = "执行人ID（user_id逻辑外键）")
    private String executorId;
	/**执行状态：0-待执行，1-已完成，2-跳过，3-异常*/
	@Excel(name = "执行状态：0-待执行，1-已完成，2-跳过，3-异常", width = 15)
    @Schema(description = "执行状态：0-待执行，1-已完成，2-跳过，3-异常")
    private String status;
	/**异常情况描述（如"老人拒食"、"皮肤红肿"）*/
	@Excel(name = "异常情况描述（如老人拒食、皮肤红肿）", width = 15)
    @Schema(description = "异常情况描述（如老人拒食、皮肤红肿）")
    private String abnormalDesc;
	/**拍照凭证URL（如翻身后的皮肤状况）*/
	@Excel(name = "拍照凭证URL（如翻身后的皮肤状况）", width = 15)
    @Schema(description = "拍照凭证URL（如翻身后的皮肤状况）")
    private String evidenceUrl;
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
    @Schema(description = "执行人")
    private String executorName;
}
