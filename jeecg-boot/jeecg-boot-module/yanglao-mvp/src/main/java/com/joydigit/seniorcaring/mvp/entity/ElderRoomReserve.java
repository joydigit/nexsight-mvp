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
 * @Description: 房间预定
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Data
@TableName("elder_room_reserve")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="房间预定")
public class ElderRoomReserve implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private String id;
	/**客户id*/
	@Excel(name = "客户id", width = 15)
    @Schema(description = "客户id")
    private String customerId;
	/**咨询接待id*/
	@Excel(name = "咨询接待id", width = 15)
    @Schema(description = "咨询接待id")
    private String consultingId;
	/**房间id*/
	@Excel(name = "房间id", width = 15)
    @Schema(description = "房间id")
    private String roomId;
	/**床位id*/
	@Excel(name = "床位id", width = 15)
    @Schema(description = "床位id")
    private String bedId;
	/**状态 1 预定 2 取消预定*/
	@Excel(name = "状态 1 预定 2 取消预定", width = 15)
    @Schema(description = "状态 1 预定 2 取消预定")
    private String status;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
    @Schema(description = "项目ID")
    private String projectId;
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
    private String buildingId;

    @TableField(exist = false)
    private String floorId;

    @TableField(exist = false)
    private String roomNo;

    @TableField(exist = false)
    private String bedNo;

    @TableField(exist = false)
    private List<String> projectIds;

}
