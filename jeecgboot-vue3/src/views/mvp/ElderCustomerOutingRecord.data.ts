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
    title: '开始时间',
    align: "center",
    dataIndex: 'startTime'
  },
  {
    title: '结束时间',
    align: "center",
    dataIndex: 'endTime'
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
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  endTime: {title: '结束时间',order: 2,view: 'datetime', type: 'string',},
  startTime: {title: '开始时间',order: 3,view: 'datetime', type: 'string',},
  checkinId: {title: '入住id',order: 4,view: 'text', type: 'string',},
  customerId: {title: '客户id',order: 5,view: 'text', type: 'string',},
  remark: {title: '备注',order: 6,view: 'text', type: 'string',},
};
