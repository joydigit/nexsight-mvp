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
 * @Description: elder_project_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_project_fee_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_project_fee_config")
public class ElderProjectFeeConfig implements Serializable {
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
	/**费用类型名称*/
	@Excel(name = "费用类型名称", width = 15)
    @Schema(description = "费用类型名称")
    private String paymentTypeName;
	/**费用类型编码*/
	@Excel(name = "费用类型编码", width = 15)
    @Schema(description = "费用类型编码")
    private String paymentTypeCode;
	/**单价（元）*/
	@Excel(name = "单价（元）", width = 15)
    @Schema(description = "单价（元）")
    private BigDecimal price;
	/**单位编码*/
	@Excel(name = "单位编码", width = 15)
    @Schema(description = "单位编码")
    private String unitCode;
	/**单位名称*/
	@Excel(name = "单位名称", width = 15)
    @Schema(description = "单位名称")
    private String unitName;
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
}
