import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '入住编码',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '记录时间',
    align: "center",
    dataIndex: 'recordTime'
  },
  {
    title: '体温',
    align: "center",
    dataIndex: 'temperature'
  },
  {
    title: '收缩压/高压（mmHg）',
    align: "center",
    dataIndex: 'bloodPressureHigh'
  },
  {
    title: '舒张压/低压（mmHg）',
    align: "center",
    dataIndex: 'bloodPressureLow'
  },
  {
    title: '心率（次/分）',
    align: "center",
    dataIndex: 'heartRate'
  },
  {
    title: '血糖（mmol/L）',
    align: "center",
    dataIndex: 'bloodSugar'
  },
  {
    title: '呼吸（次/分）',
    align: "center",
    dataIndex: 'respiration'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户ID',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  recordTime: {title: '记录时间',order: 4,view: 'datetime', type: 'string',},
  temperature: {title: '体温（°C），如37.5',order: 5,view: 'text', type: 'string',},
  bloodPressureHigh: {title: '收缩压/高压（mmHg），如140',order: 6,view: 'text', type: 'string',},
  bloodPressureLow: {title: '舒张压/低压（mmHg），如90',order: 7,view: 'text', type: 'string',},
  heartRate: {title: '心率（次/分），如75',order: 8,view: 'text', type: 'string',},
  bloodSugar: {title: '血糖（mmol/L），如5.8',order: 9,view: 'text', type: 'string',},
  respiration: {title: '呼吸（次/分），如20',order: 10,view: 'text', type: 'string',},
  remark: {title: '备注（如"餐后2小时"）',order: 11,view: 'text', type: 'string',},
};
