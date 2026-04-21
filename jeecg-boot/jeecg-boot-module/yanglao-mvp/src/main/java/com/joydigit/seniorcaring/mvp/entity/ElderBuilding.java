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
 * @Description: elder_building
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_building")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_building")
public class ElderBuilding implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键，雪花ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
    private String id;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @Schema(description = "租户ID")
    private String tenantId;
	/**项目ID（逻辑外键）*/
	@Excel(name = "项目ID（逻辑外键）", width = 15)
    @Schema(description = "项目ID（逻辑外键）")
    private String projectId;
	/**楼栋名称（如A栋、1号楼）*/
	@Excel(name = "楼栋名称（如A栋、1号楼）", width = 15)
    @Schema(description = "楼栋名称（如A栋、1号楼）")
    private String buildingName;
	/**排序号（展示顺序）*/
	@Excel(name = "排序号（展示顺序）", width = 15)
    @Schema(description = "排序号（展示顺序）")
    private Integer sortOrder;
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
