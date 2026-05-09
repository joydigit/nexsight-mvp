import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '账单编号',
    align: "center",
    dataIndex: 'billNo'
  },
  {
    title: '费用类型',
    align: "center",
    dataIndex: 'itemType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'payment_type');
    },
  },  
  {
    title: '数量',
    align: "center",
    dataIndex: 'quantity'
  },
  {
    title: '单价（元）',
    align: "center",
    dataIndex: 'unitPrice'
  },
  {
    title: '金额（元',
    align: "center",
    dataIndex: 'amount'
  }  
];

// 高级查询数据
export const superQuerySchema = {
  
};
