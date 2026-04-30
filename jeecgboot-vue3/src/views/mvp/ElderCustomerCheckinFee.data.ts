import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '入住编码',
    align: "center",
    dataIndex: 'checkinId'
  }, 
  {
    title: '费用类型',
    align: "center",
    dataIndex: 'paymentTypeCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'payment_type');
    },
  },
  {
    title: '金额(元)',
    align: "center",
    dataIndex: 'amount'
  },
  {
    title: '单位',
    align: "center",
    dataIndex: 'unitCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'fee_unit');
    },
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  feeType: {title: '费用类型 1 床费 2 其它费用',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  feeConfigId: {title: '费用配置id',order: 4,view: 'text', type: 'string',},
};
