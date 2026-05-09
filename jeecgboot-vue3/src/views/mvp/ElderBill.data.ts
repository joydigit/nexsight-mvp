import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '项目',
    align: "center",
    dataIndex: 'projectName'
  },
  {
    title: '账单号',
    align: "center",
    dataIndex: 'billNo'
  },
  {
    title: '客户',
    align: "center",
    dataIndex: 'customerName'
  },
  {
    title: '入住编号',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '账单月份',
    align: "center",
    dataIndex: 'billMonth'
  },
  {
    title: '应收总额（元）',
    align: "center",
    dataIndex: 'totalAmount'
  },
  {
    title: '实收总额（元）',
    align: "center",
    dataIndex: 'paidAmount'
  },
  {
    title: '优惠/减免金额（元）',
    align: "center",
    dataIndex: 'discountAmount'
  },
  {
    title: '账单状态',
    align: "center",
    dataIndex: 'status',
    customRender: ({ text }) => {
      return render.renderDict(text, 'bill_status');
    },
  },
  {
    title: '应收日期',
    align: "center",
    dataIndex: 'dueDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
  } 
];

// 高级查询数据
export const superQuerySchema = {
 
};
