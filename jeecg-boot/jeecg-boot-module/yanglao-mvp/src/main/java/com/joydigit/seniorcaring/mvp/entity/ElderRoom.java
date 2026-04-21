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
 * @Description: elder_room
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_room")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_room")
public class ElderRoom implements Serializable {
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
	/**楼栋ID*/
	@Excel(name = "楼栋ID", width = 15)
    @Schema(description = "楼栋ID")
    private String buildingId;
	/**楼层id*/
	@Excel(name = "楼层id", width = 15)
    @Schema(description = "楼层id")
    private String floorId;
	/**房号*/
	@Excel(name = "房号", width = 15)
    @Schema(description = "房号")
    private String roomNo;
	/**房型名称*/
	@Excel(name = "房型名称", width = 15)
    @Schema(description = "房型名称")
    private String roomTypeName;
	/**房型：SINGLE单人间/DOUBLE双人间/MULTI多人间/SUITE套房*/
	@Excel(name = "房型：SINGLE单人间/DOUBLE双人间/MULTI多人间/SUITE套房", width = 15)
    @Schema(description = "房型：SINGLE单人间/DOUBLE双人间/MULTI多人间/SUITE套房")
    private String roomType;
	/**面积（平方米）*/
	@Excel(name = "面积（平方米）", width = 15)
    @Schema(description = "面积（平方米）")
    private String area;
	/**状态：0-空闲，1-预订，2-入住，3-维修，4-停用*/
	@Excel(name = "状态：0-空闲，1-预订，2-入住，3-维修，4-停用", width = 15)
    @Schema(description = "状态：0-空闲，1-预订，2-入住，3-维修，4-停用")
    private String status;
	/**备注（如装修说明）*/
	@Excel(name = "备注（如装修说明）", width = 15)
    @Schema(description = "备注（如装修说明）")
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
