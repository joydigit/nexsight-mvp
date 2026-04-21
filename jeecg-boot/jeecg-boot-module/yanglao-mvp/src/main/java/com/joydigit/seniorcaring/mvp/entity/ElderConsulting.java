package com.joydigit.seniorcaring.mvp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import org.jeecg.common.aspect.annotation.AutoDict;
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
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_consulting")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_consulting")
public class ElderConsulting implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**租户ID*/
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**所属项目ID*/

    @Schema(description = "所属项目ID")
    private String projectId;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @Schema(description = "姓名")
    private String name;
	/**性别：0-女，1-男，2-未知*/
	@Excel(name = "性别", width = 15)
    @Schema(description = "性别：0-女，1-男，2-未知")
    @Dict(dicCode = "gender_type")
    private String gender;
	/**咨询类型名称*/
	@Excel(name = "咨询类型名称", width = 15)
    @Schema(description = "咨询类型名称")
    private String consultTypeName;
	/**咨询类型编码*/
	@Excel(name = "咨询类型编码", width = 15)
    @Schema(description = "咨询类型编码")
    @Dict(dicCode = "consult_type")
    private String consultTypeCode;
	/**跟进状态：0-待跟进，1-已跟进，2-已放弃*/
	@Excel(name = "跟进状态", width = 15)
    @Schema(description = "跟进状态：0-待跟进，1-已跟进，2-已放弃")
    @Dict(dicCode = "follow_status")
    private String followStatus;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    @Schema(description = "身份证号")
    private String idCard;
	/**证件类型*/
	@Excel(name = "证件类型", width = 15)
    @Schema(description = "证件类型")
    @Dict(dicCode = "id_card_type")
    private String idCardType;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @Schema(description = "地址")
    private String address;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @Schema(description = "联系电话")
    private String phone;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "出生日期")
    private Date birthDate;
	/**接待人名称*/
	@Excel(name = "接待人名称", width = 15)
    @Schema(description = "接待人名称")
    private String receptionistName;
	/**接待人id*/
	@Excel(name = "接待人id", width = 15)
    @Schema(description = "接待人id")
    private String receptionistId;
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
    @Schema(description = "删除状态：0正常，1已删除")
    @TableLogic
    private Integer delFlag;
    /**
     * 项目名称
     */
    @Excel(name = "所属项目", width = 15)
    @TableField(exist = false)
    private String projectName;
}
