import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '租户ID',
    align: "center",
    dataIndex: 'tenantId'
  },
  {
    title: '项目ID',
    align: "center",
    dataIndex: 'projectId'
  },
  {
    title: '客户ID',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '入住id',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '账单ID',
    align: "center",
    dataIndex: 'billId'
  },
  {
    title: '收款类型名称',
    align: "center",
    dataIndex: 'paymentTypeName'
  },
  {
    title: '收款类型编码',
    align: "center",
    dataIndex: 'paymentTypeCode'
  },
  {
    title: '金额（元），退款时为负数',
    align: "center",
    dataIndex: 'amount'
  },
  {
    title: '支付方式名称',
    align: "center",
    dataIndex: 'paymentMethodName'
  },
  {
    title: '支付方式编码',
    align: "center",
    dataIndex: 'paymentMethodCode'
  },
  {
    title: '第三方支付流水号（微信/支付宝订单号）',
    align: "center",
    dataIndex: 'transactionNo'
  },
  {
    title: '收款日期（业务日期）',
    align: "center",
    dataIndex: 'paymentDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
  },
  {
    title: '凭证图片URL（收据拍照）',
    align: "center",
    dataIndex: 'voucherUrl'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户ID',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  billId: {title: '账单ID',order: 4,view: 'text', type: 'string',},
  paymentTypeName: {title: '收款类型名称',order: 5,view: 'text', type: 'string',},
  paymentTypeCode: {title: '收款类型编码',order: 6,view: 'text', type: 'string',},
  amount: {title: '金额（元），退款时为负数',order: 7,view: 'number', type: 'number',},
  paymentMethodName: {title: '支付方式名称',order: 8,view: 'text', type: 'string',},
  paymentMethodCode: {title: '支付方式编码',order: 9,view: 'text', type: 'string',},
  transactionNo: {title: '第三方支付流水号（微信/支付宝订单号）',order: 10,view: 'text', type: 'string',},
  paymentDate: {title: '收款日期（业务日期）',order: 11,view: 'date', type: 'string',},
  voucherUrl: {title: '凭证图片URL（收据拍照）',order: 12,view: 'text', type: 'string',},
};
