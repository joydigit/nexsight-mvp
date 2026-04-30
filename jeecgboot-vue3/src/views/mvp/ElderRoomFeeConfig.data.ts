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
    title: '房间',
    align: "center",
    dataIndex: 'roomNo'
  },
  {
    title: '床位',
    align: "center",
    dataIndex: 'bedNo'
  },
  {
    title: '房费类型',
    align: "center",
    dataIndex: 'itemType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'room_fee_type');
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
    title: '费用(元)',
    align: "center",
    dataIndex: 'price'
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
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  itemId: {title: '房间id/床位id',order: 2,view: 'text', type: 'string',},
  itemType: {title: '费用项类型 1 房间 2 床位',order: 3,view: 'text', type: 'string',},
  price: {title: '费用',order: 4,view: 'number', type: 'number',},
  remark: {title: '备注',order: 5,view: 'text', type: 'string',},
};
