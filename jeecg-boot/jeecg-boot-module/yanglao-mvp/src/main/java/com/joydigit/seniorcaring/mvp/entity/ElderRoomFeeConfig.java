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
 * @Description: elder_room_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_room_fee_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_room_fee_config")
public class ElderRoomFeeConfig implements Serializable {
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
	/**房间id/床位id*/
	@Excel(name = "房间id/床位id", width = 15)
    @Schema(description = "房间id/床位id")
    private String itemId;
	/**费用项类型 1 房间 2 床位*/
	@Excel(name = "费用项类型 1 房间 2 床位", width = 15)
    @Schema(description = "费用项类型 1 房间 2 床位")
    private String itemType;
	/**费用*/
	@Excel(name = "费用", width = 15)
    @Schema(description = "费用")
    private BigDecimal price;
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
    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private String buildingName;
    @TableField(exist = false)
    private String floorName;
    @TableField(exist = false)
    private String roomNo;
    @TableField(exist = false)
    private String bedNo;

    @TableField(exist = false)
    private List<String> projectIds;
}
