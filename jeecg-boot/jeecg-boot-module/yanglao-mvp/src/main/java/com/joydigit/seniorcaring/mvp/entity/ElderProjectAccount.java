package com.joydigit.seniorcaring.mvp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 项目账户配置
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Data
@TableName("elder_project_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="项目账户配置")
public class ElderProjectAccount implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private String id;
	/**项目id*/
	@Excel(name = "项目id", width = 15)
    @Schema(description = "项目id")
    private String projectId;
	/**账户类型*/
	@Excel(name = "账户类型", width = 15)
    @Schema(description = "账户类型")
    private String accountTypeCode;
    /**状态：0-失效，1-有效*/
    @Excel(name = "状态：0-失效，1-有效", width = 15)
    @Schema(description = "状态：0-失效，1-有效")
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
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private Integer tenantId;
    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String projectName;

    @TableField(exist = false)
    private List<String> projectIds;
}
