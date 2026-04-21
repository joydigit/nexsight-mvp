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
    title: '销售名称',
    align: "center",
    dataIndex: 'salesName'
  },
  {
    title: '咨询接待id',
    align: "center",
    dataIndex: 'consultingId'
  },
  {
    title: '销售id',
    align: "center",
    dataIndex: 'salesId'
  },
  {
    title: '入住时间',
    align: "center",
    dataIndex: 'checkinTime'
  },
  {
    title: '合同url',
    align: "center",
    dataIndex: 'contractFileUrl'
  },
  {
    title: '合同号',
    align: "center",
    dataIndex: 'contractNo'
  },
  {
    title: '实际退住时间',
    align: "center",
    dataIndex: 'realCheckoutTime'
  },
  {
    title: '预计退住时间',
    align: "center",
    dataIndex: 'expectCheckoutTime'
  },
  {
    title: '入住类型 1长住 2短住',
    align: "center",
    dataIndex: 'checkinType'
  },
  {
    title: '状态 1 入住 2 退住',
    align: "center",
    dataIndex: 'status'
  },
  {
    title: '客户id',
    align: "center",
    dataIndex: 'customerId'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  salesName: {title: '销售名称',order: 2,view: 'text', type: 'string',},
  consultingId: {title: '咨询接待id',order: 3,view: 'text', type: 'string',},
  salesId: {title: '销售id',order: 4,view: 'text', type: 'string',},
  checkinTime: {title: '入住时间',order: 5,view: 'datetime', type: 'string',},
  contractFileUrl: {title: '合同url',order: 6,view: 'text', type: 'string',},
  contractNo: {title: '合同号',order: 7,view: 'text', type: 'string',},
  realCheckoutTime: {title: '实际退住时间',order: 8,view: 'datetime', type: 'string',},
  expectCheckoutTime: {title: '预计退住时间',order: 9,view: 'datetime', type: 'string',},
  checkinType: {title: '入住类型 1长住 2短住',order: 10,view: 'text', type: 'string',},
  status: {title: '状态 1 入住 2 退住',order: 11,view: 'text', type: 'string',},
  customerId: {title: '客户id',order: 12,view: 'text', type: 'string',},
};
