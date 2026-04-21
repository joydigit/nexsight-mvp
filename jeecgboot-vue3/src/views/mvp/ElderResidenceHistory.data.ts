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
    title: '客户ID',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '入住id',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '变更类型名称',
    align: "center",
    dataIndex: 'changeTypeName'
  },
  {
    title: '变更类型编码',
    align: "center",
    dataIndex: 'changeTypeCode'
  },
  {
    title: '原房间ID（换房/退住时填充）',
    align: "center",
    dataIndex: 'oldRoomId'
  },
  {
    title: '原床位ID（换房/退住时填充）',
    align: "center",
    dataIndex: 'oldBedId'
  },
  {
    title: '新房间ID（入住/换房时填充）',
    align: "center",
    dataIndex: 'newRoomId'
  },
  {
    title: '新床位ID（入住/换房时填充）',
    align: "center",
    dataIndex: 'newBedId'
  },
  {
    title: '变更原因（如"家属要求换向阳房间"）',
    align: "center",
    dataIndex: 'reason'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户ID',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  changeTypeName: {title: '变更类型名称',order: 4,view: 'text', type: 'string',},
  changeTypeCode: {title: '变更类型编码',order: 5,view: 'text', type: 'string',},
  oldRoomId: {title: '原房间ID（换房/退住时填充）',order: 6,view: 'text', type: 'string',},
  oldBedId: {title: '原床位ID（换房/退住时填充）',order: 7,view: 'text', type: 'string',},
  newRoomId: {title: '新房间ID（入住/换房时填充）',order: 8,view: 'text', type: 'string',},
  newBedId: {title: '新床位ID（入住/换房时填充）',order: 9,view: 'text', type: 'string',},
  reason: {title: '变更原因（如"家属要求换向阳房间"）',order: 10,view: 'text', type: 'string',},
};
