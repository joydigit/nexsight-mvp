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
    title: '账户类型',
    align: "center",
    dataIndex: 'accountTypeCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'account_type');
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
  projectId: {title: '项目id',order: 0,view: 'list', type: 'string',dictCode: '',},
  accountTypeCode: {title: '账户类型',order: 1,view: 'list', type: 'string',dictCode: '',},
  tenantId: {title: '租户ID',order: 3,view: 'number', type: 'number',},
};
