package com.joydigit.seniorcaring.mvp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: elder_assessment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_assessment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_assessment")
public class ElderAssessment implements Serializable {
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
	/**评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估*/
	@Excel(name = "评估类型", width = 15)
    @Schema(description = "评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估")
    private String assessmentTypeCode;
	/**评分（百分制或特定量表分数）*/
	@Excel(name = "评分（百分制或特定量表分数）", width = 15)
    @Schema(description = "评分（百分制或特定量表分数）")
    private Integer score;
	/**评估结果描述（如"轻度依赖"）*/
	@Excel(name = "评估结果描述（如轻度依赖）", width = 15)
    @Schema(description = "评估结果描述（如轻度依赖）")
    private String result;
	/**建议护理等级（如LEVEL_2）*/
	@Excel(name = "建议护理等级（如LEVEL_2）", width = 15)
    @Schema(description = "建议护理等级（如LEVEL_2）")
    private String suggestedCareLevel;
	/**评估人姓名*/
	@Excel(name = "评估人姓名", width = 15)
    @Schema(description = "评估人姓名")
    private String assessor;
	/**评估报告附件URL（PDF或图片）*/
	@Excel(name = "评估报告附件URL（PDF或图片）", width = 15)
    @Schema(description = "评估报告附件URL（PDF或图片）")
    private String fileUrl;
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
