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
    title: '账户类型编码',
    align: "center",
    dataIndex: 'accountTypeCode'
  },
  {
    title: '账户类型名称',
    align: "center",
    dataIndex: 'accountTypeName'
  },
  {
    title: '账户金额',
    align: "center",
    dataIndex: 'amount'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户id',order: 2,view: 'text', type: 'string',},
  accountTypeCode: {title: '账户类型编码',order: 3,view: 'text', type: 'string',},
  accountTypeName: {title: '账户类型名称',order: 4,view: 'text', type: 'string',},
  amount: {title: '账户金额',order: 5,view: 'number', type: 'number',},
};
