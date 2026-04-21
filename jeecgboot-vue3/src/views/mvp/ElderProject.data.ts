import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [ 
  {
    title: '项目编码',
    align: "center",
    dataIndex: 'projectCode'
  },
  {
    title: '项目名称',
    align: "center",
    dataIndex: 'projectName'
  },
  {
    title: '类型',
    align: "center",
    dataIndex: 'projectType',
    customRender: ({ text }) => {
      return render.renderDict(text, 'project_type');
    },
  },  
  {
    title: '状态',
    align: "center",
    dataIndex: 'status',
    customRender: ({ text }) => {
      return render.renderDict(text, 'project_status');
    }
  },
];

// 高级查询数据
export const superQuerySchema = {
  projectCode: {title: '项目编码',order: 1,view: 'text', type: 'string',},
  projectName: {title: '项目名称',order: 2,view: 'text', type: 'string',},
  projectType: {title: '类型',order: 3,view: 'text', type: 'string',},
  address: {title: '详细地址',order: 4,view: 'text', type: 'string',},
  status: {title: '状态',order: 5,view: 'text', type: 'string',},
};
