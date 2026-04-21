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
    title: '所属项目ID',
    align: "center",
    dataIndex: 'projectId'
  },
  {
    title: '费用类型 1 床费 2 其它费用',
    align: "center",
    dataIndex: 'feeType'
  },
  {
    title: '入住id',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '费用配置id',
    align: "center",
    dataIndex: 'feeConfigId'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  feeType: {title: '费用类型 1 床费 2 其它费用',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  feeConfigId: {title: '费用配置id',order: 4,view: 'text', type: 'string',},
};
