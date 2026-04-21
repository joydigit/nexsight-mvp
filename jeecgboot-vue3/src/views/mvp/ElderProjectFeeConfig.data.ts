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
    title: '费用类型名称',
    align: "center",
    dataIndex: 'paymentTypeName'
  },
  {
    title: '费用类型编码',
    align: "center",
    dataIndex: 'paymentTypeCode'
  },
  {
    title: '单价（元）',
    align: "center",
    dataIndex: 'price'
  },
  {
    title: '单位编码',
    align: "center",
    dataIndex: 'unitCode'
  },
  {
    title: '单位名称',
    align: "center",
    dataIndex: 'unitName'
  },
  {
    title: '状态：0-失效，1-有效',
    align: "center",
    dataIndex: 'status'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  paymentTypeName: {title: '费用类型名称',order: 2,view: 'text', type: 'string',},
  paymentTypeCode: {title: '费用类型编码',order: 3,view: 'text', type: 'string',},
  price: {title: '单价（元）',order: 4,view: 'number', type: 'number',},
  unitCode: {title: '单位编码',order: 5,view: 'text', type: 'string',},
  unitName: {title: '单位名称',order: 6,view: 'text', type: 'string',},
  status: {title: '状态：0-失效，1-有效',order: 7,view: 'text', type: 'string',},
};
