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
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_residence_history")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_residence_history")
public class ElderResidenceHistory implements Serializable {
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
	/**变更类型名称*/
	@Excel(name = "变更类型名称", width = 15)
    @Schema(description = "变更类型名称")
    private String changeTypeName;
	/**变更类型编码*/
	@Excel(name = "变更类型编码", width = 15)
    @Schema(description = "变更类型编码")
    private String changeTypeCode;
	/**原房间ID（换房/退住时填充）*/
	@Excel(name = "原房间ID（换房/退住时填充）", width = 15)
    @Schema(description = "原房间ID（换房/退住时填充）")
    private String oldRoomId;
    @TableField(exist = false)
    private String oldRoomName;
	/**原床位ID（换房/退住时填充）*/
	@Excel(name = "原床位ID（换房/退住时填充）", width = 15)
    @Schema(description = "原床位ID（换房/退住时填充）")
    private String oldBedId;
    @TableField(exist = false)
    private String oldBedName;
	/**新房间ID（入住/换房时填充）*/
	@Excel(name = "新房间ID（入住/换房时填充）", width = 15)
    @Schema(description = "新房间ID（入住/换房时填充）")
    private String newRoomId;
    @TableField(exist = false)
    private String newRoomName;
	/**新床位ID（入住/换房时填充）*/
	@Excel(name = "新床位ID（入住/换房时填充）", width = 15)
    @Schema(description = "新床位ID（入住/换房时填充）")
    private String newBedId;
    @TableField(exist = false)
    private String newBedName;
	/**变更原因（如"家属要求换向阳房间"）*/
	@Excel(name = "变更原因（如家属要求换向阳房间）", width = 15)
    @Schema(description = "变更原因（如家属要求换向阳房间）")
    private String reason;
	/**创建人（操作人）*/
    @Schema(description = "创建人（操作人）")
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
