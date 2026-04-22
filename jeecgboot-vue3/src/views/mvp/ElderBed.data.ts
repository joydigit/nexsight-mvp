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
    title: '楼栋',
    align: "center",
    dataIndex: 'buildingName'
  },
  {
    title: '楼层',
    align: "center",
    dataIndex: 'floorName'
  },
  {
    title: '房间',
    align: "center",
    dataIndex: 'roomNo'
  },
  {
    title: '床位号',
    align: "center",
    dataIndex: 'bedNo'
  },
  {
    title: '状态',
    align: "center",
    dataIndex: 'status',
    customRender: ({ text }) => {
      return render.renderDict(text, 'room_status');
    },
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  roomId: {title: '房间ID',order: 2,view: 'text', type: 'string',},
  bedNo: {title: '床位号（如A床、B床、1床、2床）',order: 3,view: 'text', type: 'string',},
  status: {title: '状态：0-空闲，1-预订，2-入住，3-维修，4-停用',order: 4,view: 'text', type: 'string',},
};
