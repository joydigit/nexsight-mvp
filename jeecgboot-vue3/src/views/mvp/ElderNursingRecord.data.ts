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
    title: '执行人',
    align: "center",
    dataIndex: 'executorName'
  },  
  {
    title: '护理时间',
    align: "center",
    dataIndex: 'nursingDate'
  },
  {
    title: '护理项目',
    align: "center",
    dataIndex: 'itemCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'nursing_item_type');
    },
  },
  {
    title: '执行状态',
    align: "center",
    dataIndex: 'status',
    customRender: ({ text }) => {
      return render.renderDict(text, 'nursing_record_status');
    },
  },
  {
    title: '异常情况描述',
    align: "center",
    dataIndex: 'abnormalDesc'
  } 
];

// 高级查询数据
export const superQuerySchema = {
  
};
