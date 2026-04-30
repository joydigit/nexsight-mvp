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
    title: '费用类型',
    align: "center",
    dataIndex: 'paymentTypeCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'payment_type');
    },
  },
  {
    title: '房间类型',
    align: "center",
    dataIndex: 'roomType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'room_type');
    },
  },
  {
    title: '入住类型',
    align: "center",
    dataIndex: 'checkinType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'checkin_type');
    },
  },
  {
    title: '单价（元）',
    align: "center",
    dataIndex: 'price'
  },
  {
    title: '单位',
    align: "center",
    dataIndex: 'unitCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'fee_unit');
    },
  },  
  {
    title: '状态',
    align: "center",
    dataIndex: 'status',
    customRender: ({ text }) => {
      return render.renderDict(text, 'fee_status');
    },
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  paymentTypeName: {title: '费用类型名称',order: 2,view: 'text', type: 'string',},
  paymentTypeCode: {title: '费用类型编码',order: 3,view: 'text', type: 'string',},
  price: {title: '单价（元）',order: 4,view: 'number', type: 'number',},
  unitCode: {title: '单位编码',order: 5,view: 'text', type: 'string',},
  unitName: {title: '单位名称',order: 6,view: 'text', type: 'string',},
  status: {title: '状态：0-失效，1-有效',order: 7,view: 'text', type: 'string',},
};
