import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '租户ID（SaaS集团隔离）',
    align: "center",
    dataIndex: 'tenantId'
  },
  {
    title: '项目编码（如A001，用于账单号前缀）',
    align: "center",
    dataIndex: 'projectCode'
  },
  {
    title: '项目名称（如阳光养老社区）',
    align: "center",
    dataIndex: 'projectName'
  },
  {
    title: '类型：HIGH_END高端/STANDARD普惠/MEDICAL医养结合',
    align: "center",
    dataIndex: 'projectType'
  },
  {
    title: '详细地址',
    align: "center",
    dataIndex: 'address'
  },
  {
    title: '状态：0 未启用 1 启用',
    align: "center",
    dataIndex: 'status'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID（SaaS集团隔离）',order: 0,view: 'text', type: 'string',},
  projectCode: {title: '项目编码（如A001，用于账单号前缀）',order: 1,view: 'text', type: 'string',},
  projectName: {title: '项目名称（如阳光养老社区）',order: 2,view: 'text', type: 'string',},
  projectType: {title: '类型：HIGH_END高端/STANDARD普惠/MEDICAL医养结合',order: 3,view: 'text', type: 'string',},
  address: {title: '详细地址',order: 4,view: 'text', type: 'string',},
  status: {title: '状态：0 未启用 1 启用',order: 5,view: 'text', type: 'string',},
};
