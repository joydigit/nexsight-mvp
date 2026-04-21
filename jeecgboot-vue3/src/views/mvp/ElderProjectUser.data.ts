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
    title: 'Jeecg系统用户ID（逻辑外键）',
    align: "center",
    dataIndex: 'userId'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID（逻辑外键）',order: 1,view: 'text', type: 'string',},
  userId: {title: 'Jeecg系统用户ID（逻辑外键）',order: 2,view: 'text', type: 'string',},
};
