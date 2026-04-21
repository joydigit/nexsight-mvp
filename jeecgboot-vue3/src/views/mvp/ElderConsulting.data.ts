import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
import { getDictItemsByCode } from '/@/utils/dict';
import { ref } from 'vue';
import { filterMultiDictText } from '/@/utils/dict/JDictSelectUtil.js';

//列表数据
export const columns: BasicColumn[] = [
  {
    title: '所属项目',
    align: "center",
    dataIndex: 'projectName'
  },
  {
    title: '姓名',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '性别',
    align: "center",
    dataIndex: 'gender',
    customRender: ({ record }) => {
      return render.renderDict(record.gender, 'gender_type');
    },
  },
  {
    title: '咨询类型',
    align: "center",
    dataIndex: 'consultTypeCode',
    customRender: ({ text }) => {
      return render.renderDict(text, 'consult_type');
    },
  },
  {
    title: '跟进状态',
    align: "center",
    dataIndex: 'followStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'follow_status');
    },
  },
  {
    title: '联系电话',
    align: "center",
    dataIndex: 'phone'
  },
  {
    title: '接待人名称',
    align: "center",
    dataIndex: 'receptionistName'
  },
];

// 高级查询数据
export const superQuerySchema = {
  tenantId: {title: '租户ID',order: 0,view: 'text', type: 'string',},
  projectId: {title: '所属项目ID',order: 1,view: 'text', type: 'string',},
  name: {title: '姓名',order: 2,view: 'text', type: 'string',},
  gender: {title: '性别',order: 3,view: 'text', type: 'string',},
  consultTypeName: {title: '咨询类型名称',order: 4,view: 'list', type: 'string',dictCode: '',},
  consultTypeCode: {title: '咨询类型编码',order: 5,view: 'text', type: 'string',},
  followStatus: {title: '跟进状态',order: 6,view: 'list', type: 'string',dictCode: '',},
  idCard: {title: '身份证号',order: 7,view: 'text', type: 'string',},
  idCardType: {title: '证件类型',order: 8,view: 'text', type: 'string',},
  address: {title: '地址',order: 9,view: 'text', type: 'string',},
  phone: {title: '联系电话',order: 10,view: 'text', type: 'string',},
  birthDate: {title: '出生日期',order: 11,view: 'date', type: 'string',},
  receptionistName: {title: '接待人名称',order: 12,view: 'text', type: 'string',},
  receptionistId: {title: '接待人id',order: 13,view: 'text', type: 'string',},
  remark: {title: '备注',order: 14,view: 'text', type: 'string',},
};
