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
    title: '楼栋ID',
    align: "center",
    dataIndex: 'buildingId'
  },
  {
    title: '楼层id',
    align: "center",
    dataIndex: 'floorId'
  },
  {
    title: '房号',
    align: "center",
    dataIndex: 'roomNo'
  },
  {
    title: '房型名称',
    align: "center",
    dataIndex: 'roomTypeName'
  },
  {
    title: '房型：SINGLE单人间/DOUBLE双人间/MULTI多人间/SUITE套房',
    align: "center",
    dataIndex: 'roomType'
  },
  {
    title: '面积（平方米）',
    align: "center",
    dataIndex: 'area'
  },
  {
    title: '状态：0-空闲，1-预订，2-入住，3-维修，4-停用',
    align: "center",
    dataIndex: 'status'
  },
  {
    title: '备注（如装修说明）',
    align: "center",
    dataIndex: 'remark'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  buildingId: {title: '楼栋ID',order: 2,view: 'text', type: 'string',},
  floorId: {title: '楼层id',order: 3,view: 'text', type: 'string',},
  roomNo: {title: '房号',order: 4,view: 'text', type: 'string',},
  roomTypeName: {title: '房型名称',order: 5,view: 'text', type: 'string',},
  roomType: {title: '房型：SINGLE单人间/DOUBLE双人间/MULTI多人间/SUITE套房',order: 6,view: 'text', type: 'string',},
  area: {title: '面积（平方米）',order: 7,view: 'text', type: 'string',},
  status: {title: '状态：0-空闲，1-预订，2-入住，3-维修，4-停用',order: 8,view: 'text', type: 'string',},
  remark: {title: '备注（如装修说明）',order: 9,view: 'text', type: 'string',},
};
