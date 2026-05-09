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
    title: '评估类型',
    align: "center",
    dataIndex: 'assessmentTypeCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'assessment_type');
    },
  },
  {
    title: '评分',
    align: "center",
    dataIndex: 'score'
  },
  {
    title: '评估结果描述',
    align: "center",
    dataIndex: 'result'
  },
  {
    title: '建议护理等级',
    align: "center",
    dataIndex: 'suggestedCareLevel',
    customRender: ({ text }) => {
      return render.renderDict(text, 'care_level');
    },
  },
  {
    title: '评估人姓名',
    align: "center",
    dataIndex: 'assessor'
  }  
];

// 高级查询数据
export const superQuerySchema = {
 
};
