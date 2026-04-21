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
    title: '评估类型名称',
    align: "center",
    dataIndex: 'assessmentTypeName'
  },
  {
    title: '评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估',
    align: "center",
    dataIndex: 'assessmentTypeCode'
  },
  {
    title: '评分（百分制或特定量表分数）',
    align: "center",
    dataIndex: 'score'
  },
  {
    title: '评估结果描述（如"轻度依赖"）',
    align: "center",
    dataIndex: 'result'
  },
  {
    title: '建议护理等级（如LEVEL_2）',
    align: "center",
    dataIndex: 'suggestedCareLevel'
  },
  {
    title: '评估人姓名',
    align: "center",
    dataIndex: 'assessor'
  },
  {
    title: '评估报告附件URL（PDF或图片）',
    align: "center",
    dataIndex: 'fileUrl'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '项目ID',order: 1,view: 'text', type: 'string',},
  customerId: {title: '客户ID',order: 2,view: 'text', type: 'string',},
  checkinId: {title: '入住id',order: 3,view: 'text', type: 'string',},
  assessmentTypeName: {title: '评估类型名称',order: 4,view: 'text', type: 'string',},
  assessmentTypeCode: {title: '评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估',order: 5,view: 'text', type: 'string',},
  score: {title: '评分（百分制或特定量表分数）',order: 6,view: 'number', type: 'number',},
  result: {title: '评估结果描述（如"轻度依赖"）',order: 7,view: 'text', type: 'string',},
  suggestedCareLevel: {title: '建议护理等级（如LEVEL_2）',order: 8,view: 'text', type: 'string',},
  assessor: {title: '评估人姓名',order: 9,view: 'text', type: 'string',},
  fileUrl: {title: '评估报告附件URL（PDF或图片）',order: 10,view: 'text', type: 'string',},
};
