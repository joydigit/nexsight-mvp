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
    title: '房间id/床位id',
    align: "center",
    dataIndex: 'itemId'
  },
  {
    title: '费用项类型 1 房间 2 床位',
    align: "center",
    dataIndex: 'itemType'
  },
  {
    title: '费用',
    align: "center",
    dataIndex: 'price'
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
  itemId: {title: '房间id/床位id',order: 2,view: 'text', type: 'string',},
  itemType: {title: '费用项类型 1 房间 2 床位',order: 3,view: 'text', type: 'string',},
  price: {title: '费用',order: 4,view: 'number', type: 'number',},
  remark: {title: '备注',order: 5,view: 'text', type: 'string',},
};
