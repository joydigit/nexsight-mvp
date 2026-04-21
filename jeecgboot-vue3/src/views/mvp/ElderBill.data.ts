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
    title: '项目ID（逻辑外键）',
    align: "center",
    dataIndex: 'projectId'
  },
  {
    title: '账单号（唯一，如A001202604001）',
    align: "center",
    dataIndex: 'billNo'
  },
  {
    title: '客户ID（逻辑外键）',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '入住id',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '账单月份（yyyy-MM，如2026-04）',
    align: "center",
    dataIndex: 'billMonth'
  },
  {
    title: '应收总额（元）',
    align: "center",
    dataIndex: 'totalAmount'
  },
  {
    title: '实收总额（元）',
    align: "center",
    dataIndex: 'paidAmount'
  },
  {
    title: '优惠/减免金额（元）',
    align: "center",
    dataIndex: 'discountAmount'
  },
  {
    title: '账单状态：0-未收款，1-部分收款，2-已结清，3-已作废',
    align: "center",
    dataIndex: 'status'
  },
  {
    title: '应收日期（如每月5日）',
    align: "center",
    dataIndex: 'dueDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
  },
  {
    title: '备注（如"首月按天折算"）',
    align: "center",
    dataIndex: 'remark'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID（逻辑外键）',order: 1,view: 'text', type: 'string',},
  billNo: {title: '账单号（唯一，如A001202604001）',order: 2,view: 'text', type: 'string',},
  customerId: {title: '客户ID（逻辑外键）',order: 3,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 4,view: 'text', type: 'string',},
  billMonth: {title: '账单月份（yyyy-MM，如2026-04）',order: 5,view: 'text', type: 'string',},
  totalAmount: {title: '应收总额（元）',order: 6,view: 'number', type: 'number',},
  paidAmount: {title: '实收总额（元）',order: 7,view: 'number', type: 'number',},
  discountAmount: {title: '优惠/减免金额（元）',order: 8,view: 'number', type: 'number',},
  status: {title: '账单状态：0-未收款，1-部分收款，2-已结清，3-已作废',order: 9,view: 'text', type: 'string',},
  dueDate: {title: '应收日期（如每月5日）',order: 10,view: 'date', type: 'string',},
  remark: {title: '备注（如"首月按天折算"）',order: 11,view: 'text', type: 'string',},
};
