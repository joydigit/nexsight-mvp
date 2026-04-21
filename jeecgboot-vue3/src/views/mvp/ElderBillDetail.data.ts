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
    title: '账单ID',
    align: "center",
    dataIndex: 'billId'
  },
  {
    title: '费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他',
    align: "center",
    dataIndex: 'itemType'
  },
  {
    title: '费用名称（如"床位费-单人间"、"护理费-特级"）',
    align: "center",
    dataIndex: 'itemName'
  },
  {
    title: '数量（如15天，或1次）',
    align: "center",
    dataIndex: 'quantity'
  },
  {
    title: '单价（元）',
    align: "center",
    dataIndex: 'unitPrice'
  },
  {
    title: '金额（元）= quantity * unit_price',
    align: "center",
    dataIndex: 'amount'
  },
  {
    title: '计算说明（如"入住15天，按天折算：6000/30*15"）',
    align: "center",
    dataIndex: 'calcDesc'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  billId: {title: '账单ID',order: 2,view: 'text', type: 'string',},
  itemType: {title: '费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他',order: 3,view: 'text', type: 'string',},
  itemName: {title: '费用名称（如"床位费-单人间"、"护理费-特级"）',order: 4,view: 'text', type: 'string',},
  quantity: {title: '数量（如15天，或1次）',order: 5,view: 'number', type: 'number',},
  unitPrice: {title: '单价（元）',order: 6,view: 'number', type: 'number',},
  amount: {title: '金额（元）= quantity * unit_price',order: 7,view: 'number', type: 'number',},
  calcDesc: {title: '计算说明（如"入住15天，按天折算：6000/30*15"）',order: 8,view: 'text', type: 'string',},
};
