import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '项目',
    align: "center",
    dataIndex: 'projectName'
  },
  {
    title: '楼栋',
    align: "center",
    dataIndex: 'buildingName'
  },
  {
    title: '楼层名称',
    align: "center",
    dataIndex: 'floorName'
  },
  {
    title: '排序号',
    align: "center",
    dataIndex: 'sortOrder'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  buildingId: {title: '楼栋id',order: 1,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 2,view: 'text', type: 'string',},
  floorName: {title: '楼层名称',order: 3,view: 'text', type: 'string',},
  sortOrder: {title: '排序号',order: 4,view: 'number', type: 'number',},
};
