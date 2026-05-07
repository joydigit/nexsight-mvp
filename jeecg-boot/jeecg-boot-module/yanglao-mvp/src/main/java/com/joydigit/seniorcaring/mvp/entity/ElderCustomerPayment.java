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
 * @Description: elder_customer_payment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Data
@TableName("elder_customer_payment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="elder_customer_payment")
public class ElderCustomerPayment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**租户ID*/
    @Schema(description = "租户ID")
    private Integer tenantId;
	/**项目ID*/

    @Schema(description = "项目ID")
    private String projectId;
	/**客户ID*/
    @Schema(description = "客户ID")
    private String customerId;
    @Schema(description = "账户id")
    private String accountId;
    @Schema(description = "备注")
    private String remark;
    @Excel(name = "确认时间", width = 15, format = "yyyy-MM-dd HH:mm:ss",orderNum = "10")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "确认时间")
    private Date confirmTime;
    @Schema(description = "确认用户")
    private String confirmUserId;
    @Excel(name = "确认状态", width = 15,orderNum = "9",dicCode = "payment_confirm_flag")
    @Schema(description = "确认状态")
    private String confirmFlag;
	/**入住id*/
	@Excel(name = "入住编码", width = 15,orderNum = "2")
    @Schema(description = "入住id")
    private String checkinId;
	/**账单ID*/
    @Schema(description = "账单ID")
    private String billId;
	/**金额（元），退款时为负数*/
	@Excel(name = "金额（元）", width = 15, orderNum = "5")
    @Schema(description = "金额（元），退款时为负数")
    private BigDecimal amount;
	/**支付方式名称*/
	@Excel(name = "支付方式", width = 15, orderNum = "6")
    @Schema(description = "支付方式名称")
    private String paymentMethodName;
	/**支付方式编码*/
    @Schema(description = "支付方式编码")
    private String paymentMethodCode;
	/**第三方支付流水号（微信/支付宝订单号）*/
	@Excel(name = "第三方支付流水号", width = 15, orderNum = "7")
    @Schema(description = "第三方支付流水号（微信/支付宝订单号）")
    private String transactionNo;
	/**收款日期（业务日期）*/
	@Excel(name = "收款日期", width = 15, format = "yyyy-MM-dd",orderNum = "8")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "收款日期（业务日期）")
    private Date paymentDate;
	/**凭证图片URL（收据拍照）*/
    @Schema(description = "凭证图片URL（收据拍照）")
    private String voucherUrl;
	/**创建人（收款人）*/
    @Schema(description = "创建人（收款人）")
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
    @Schema(description = "交易类型")
    @Excel(name = "交易类型", width = 15,orderNum = "4",dicCode = "transaction_type_code")
    private String transactionTypeCode;
    @TableField(exist = false)
    @Excel(name = "客户", width = 15,orderNum = "1")
    private String customerName;
    @Excel(name = "项目", width = 15,orderNum = "0")
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    @Excel(name = "账单编号", width = 15,orderNum = "3")
    private String billNo;
    @TableField(exist = false)
    private List<String> projectIds;
}
