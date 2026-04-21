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
    title: '接待人名称id',
    align: "center",
    dataIndex: 'receptionistId'
  },
  {
    title: '接待人名称',
    align: "center",
    dataIndex: 'receptionistName'
  },
  {
    title: '回访类型编码',
    align: "center",
    dataIndex: 'followTypeCode'
  },
  {
    title: '回访类型名称',
    align: "center",
    dataIndex: 'followTypeName'
  },
  {
    title: '线索客户id',
    align: "center",
    dataIndex: 'consultingId'
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
  receptionistId: {title: '接待人名称id',order: 2,view: 'text', type: 'string',},
  receptionistName: {title: '接待人名称',order: 3,view: 'text', type: 'string',},
  followTypeCode: {title: '回访类型编码',order: 4,view: 'text', type: 'string',},
  followTypeName: {title: '回访类型名称',order: 5,view: 'text', type: 'string',},
  consultingId: {title: '线索客户id',order: 6,view: 'text', type: 'string',},
  remark: {title: '备注',order: 7,view: 'text', type: 'string',},
};
