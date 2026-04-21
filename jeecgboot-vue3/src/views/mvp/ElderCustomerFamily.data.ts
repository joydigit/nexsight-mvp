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
    title: '客户id',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '姓名',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '性别：0-女，1-男，2-未知',
    align: "center",
    dataIndex: 'gender'
  },
  {
    title: '身份证号',
    align: "center",
    dataIndex: 'idCard'
  },
  {
    title: '证件类型',
    align: "center",
    dataIndex: 'idCardType'
  },
  {
    title: '联系电话',
    align: "center",
    dataIndex: 'phone'
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
  customerId: {title: '客户id',order: 2,view: 'text', type: 'string',},
  name: {title: '姓名',order: 3,view: 'text', type: 'string',},
  gender: {title: '性别：0-女，1-男，2-未知',order: 4,view: 'text', type: 'string',},
  idCard: {title: '身份证号',order: 5,view: 'text', type: 'string',},
  idCardType: {title: '证件类型',order: 6,view: 'text', type: 'string',},
  phone: {title: '联系电话',order: 7,view: 'text', type: 'string',},
  remark: {title: '备注',order: 8,view: 'text', type: 'string',},
};
