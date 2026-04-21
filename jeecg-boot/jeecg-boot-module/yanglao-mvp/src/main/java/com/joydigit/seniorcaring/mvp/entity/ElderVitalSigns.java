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
 * @Description: elder_vital_signs
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_vital_signs")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_vital_signs")
public class ElderVitalSigns implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键，雪花ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
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
	/**记录时间*/
	@Excel(name = "记录时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "记录时间")
    private Date recordTime;
	/**体温（°C），如37.5*/
	@Excel(name = "体温（°C），如37.5", width = 15)
    @Schema(description = "体温（°C），如37.5")
    private String temperature;
	/**收缩压/高压（mmHg），如140*/
	@Excel(name = "收缩压/高压（mmHg），如140", width = 15)
    @Schema(description = "收缩压/高压（mmHg），如140")
    private String bloodPressureHigh;
	/**舒张压/低压（mmHg），如90*/
	@Excel(name = "舒张压/低压（mmHg），如90", width = 15)
    @Schema(description = "舒张压/低压（mmHg），如90")
    private String bloodPressureLow;
	/**心率（次/分），如75*/
	@Excel(name = "心率（次/分），如75", width = 15)
    @Schema(description = "心率（次/分），如75")
    private String heartRate;
	/**血糖（mmol/L），如5.8*/
	@Excel(name = "血糖（mmol/L），如5.8", width = 15)
    @Schema(description = "血糖（mmol/L），如5.8")
    private String bloodSugar;
	/**呼吸（次/分），如20*/
	@Excel(name = "呼吸（次/分），如20", width = 15)
    @Schema(description = "呼吸（次/分），如20")
    private String respiration;
	/**备注（如"餐后2小时"）*/
	@Excel(name = "备注（如餐后2小时）", width = 15)
    @Schema(description = "备注（如餐后2小时）")
    private String remark;
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
