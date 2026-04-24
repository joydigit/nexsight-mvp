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
 * @Description: elder_consulting_follow_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_consulting_follow_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_consulting_follow_record")
public class ElderConsultingFollowRecord implements Serializable {
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
	/**接待人名称id*/
	@Excel(name = "接待人名称id", width = 15)
    @Schema(description = "接待人名称id")
    private String receptionistId;
	/**接待人名称*/
	@Excel(name = "接待人名称", width = 15)
    @Schema(description = "接待人名称")
    private String receptionistName;
	/**回访类型编码*/
	@Excel(name = "回访类型编码", width = 15)
    @Schema(description = "回访类型编码")
    private String followTypeCode;
	/**线索客户id*/
	@Excel(name = "线索客户id", width = 15)
    @Schema(description = "线索客户id")
    private String consultingId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private String remark;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;
	/**回访时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "回访时间")
    private Date followTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private Date updateTime;
	/**删除状态：0正常，1已删除*/
	@Excel(name = "删除状态", width = 15)
    @Schema(description = "删除状态：0正常，1已删除")
    @TableLogic
    private Integer delFlag;
    /**
     * 跟进状态
     */
    private String followStatus;

    @TableField(exist = false)
    private String consultingName;
}
