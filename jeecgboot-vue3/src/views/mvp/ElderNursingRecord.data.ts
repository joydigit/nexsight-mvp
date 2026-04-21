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
    title: '项目ID',
    align: "center",
    dataIndex: 'projectId'
  },
  {
    title: '客户ID',
    align: "center",
    dataIndex: 'customerId'
  },
  {
    title: '入住id',
    align: "center",
    dataIndex: 'checkinId'
  },
  {
    title: '护理时间',
    align: "center",
    dataIndex: 'nursingDate'
  },
  {
    title: '护理项目编码（如ORAL_CARE口腔护理）',
    align: "center",
    dataIndex: 'itemCode'
  },
  {
    title: '护理项目名称',
    align: "center",
    dataIndex: 'itemName'
  },
  {
    title: '执行人ID（user_id逻辑外键）',
    align: "center",
    dataIndex: 'executorId'
  },
  {
    title: '执行开始时间',
    align: "center",
    dataIndex: 'executeStartTime'
  },
  {
    title: '执行结束时间',
    align: "center",
    dataIndex: 'executeEndTime'
  },
  {
    title: '执行状态：0-待执行，1-已完成，2-跳过，3-异常',
    align: "center",
    dataIndex: 'status'
  },
  {
    title: '异常情况描述（如"老人拒食"、"皮肤红肿"）',
    align: "center",
    dataIndex: 'abnormalDesc'
  },
  {
    title: '拍照凭证URL（如翻身后的皮肤状况）',
    align: "center",
    dataIndex: 'evidenceUrl'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户ID',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  nursingDate: {title: '护理时间',order: 4,view: 'datetime', type: 'string',},
  itemCode: {title: '护理项目编码（如ORAL_CARE口腔护理）',order: 5,view: 'text', type: 'string',},
  itemName: {title: '护理项目名称',order: 6,view: 'text', type: 'string',},
  executorId: {title: '执行人ID（user_id逻辑外键）',order: 7,view: 'text', type: 'string',},
  executeStartTime: {title: '执行开始时间',order: 8,view: 'datetime', type: 'string',},
  executeEndTime: {title: '执行结束时间',order: 9,view: 'datetime', type: 'string',},
  status: {title: '执行状态：0-待执行，1-已完成，2-跳过，3-异常',order: 10,view: 'text', type: 'string',},
  abnormalDesc: {title: '异常情况描述（如"老人拒食"、"皮肤红肿"）',order: 11,view: 'text', type: 'string',},
  evidenceUrl: {title: '拍照凭证URL（如翻身后的皮肤状况）',order: 12,view: 'text', type: 'string',},
};
