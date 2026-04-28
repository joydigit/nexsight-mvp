import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '客户id',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '咨询接待id',
    align: "center",
    dataIndex: 'consultingId'
  },
  {
    title: '房间id',
    align: "center",
    dataIndex: 'roomId'
  },
  {
    title: '床位id',
    align: "center",
    dataIndex: 'bedId'
  },
  {
    title: '状态 1 预定 2 取消预定',
    align: "center",
    dataIndex: 'status'
  },
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
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];

// 高级查询数据
export const superQuerySchema = {
  customerId: {title: '客户id',order: 0,view: 'text', type: 'string',},
  consultingId: {title: '咨询接待id',order: 1,view: 'text', type: 'string',},
  roomId: {title: '房间id',order: 2,view: 'text', type: 'string',},
  bedId: {title: '床位id',order: 3,view: 'text', type: 'string',},
  status: {title: '状态 1 预定 2 取消预定',order: 4,view: 'text', type: 'string',},
  tenantId: {title: '租户ID',order: 5,view: 'number', type: 'number',},
  projectId: {title: '项目ID',order: 6,view: 'text', type: 'string',},
  remark: {title: '备注',order: 7,view: 'text', type: 'string',},
};
