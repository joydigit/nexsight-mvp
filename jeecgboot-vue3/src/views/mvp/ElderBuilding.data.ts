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
    title: '楼栋名称',
    align: "center",
    dataIndex: 'buildingName'
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
  projectId: {title: '项目ID（逻辑外键）',order: 1,view: 'text', type: 'string',},
  buildingName: {title: '楼栋名称（如A栋、1号楼）',order: 2,view: 'text', type: 'string',},
  sortOrder: {title: '排序号（展示顺序）',order: 3,view: 'number', type: 'number',},
};
