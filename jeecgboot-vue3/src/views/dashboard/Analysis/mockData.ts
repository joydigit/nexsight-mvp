// KPI 指标卡片数据
export interface KpiCardItem {
  title: string;
  value: number;
  icon: string;
  color: string;
  prefix?: string;
  suffix?: string;
}

export const kpiCardList: KpiCardItem[] = [
  { title: '在住老人', value: 186, icon: 'ant-design:team-outlined', color: '#1890ff', suffix: '人' },
  { title: '空房数', value: 23, icon: 'ant-design:home-outlined', color: '#52c41a', suffix: '间' },
  { title: '总床位', value: 320, icon: 'ant-design:bank-outlined', color: '#722ed1', suffix: '张' },
  { title: '本月入住', value: 12, icon: 'ant-design:user-add-outlined', color: '#13c2c2', suffix: '人' },
  { title: '待缴费金额', value: 56800, icon: 'ant-design:dollar-outlined', color: '#fa8c16', prefix: '¥' },
  { title: '咨询待跟进', value: 8, icon: 'ant-design:phone-outlined', color: '#f5222d', suffix: '条' },
];

// 房间状态分布
export interface ChartItem {
  name: string;
  value: number;
}

export const roomStatusData: ChartItem[] = [
  { name: '空闲', value: 45 },
  { name: '已入住', value: 186 },
  { name: '预定', value: 12 },
  { name: '维修', value: 5 },
  { name: '停用', value: 3 },
];

// 各项目在住人数
export const projectOccupancyData: ChartItem[] = [
  { name: '阳光养老院', value: 68 },
  { name: '夕阳红公寓', value: 52 },
  { name: '康寿老年中心', value: 41 },
  { name: '福寿居养老院', value: 25 },
];

// 待跟进咨询
export interface PendingConsulting {
  id: number;
  customerName: string;
  phone: string;
  consultingDate: string;
  followPerson: string;
  status: string;
}

export const pendingConsultingList: PendingConsulting[] = [
  { id: 1, customerName: '张建国', phone: '138****6789', consultingDate: '2026-05-10', followPerson: '李经理', status: '待跟进' },
  { id: 2, customerName: '王秀英', phone: '159****8901', consultingDate: '2026-05-09', followPerson: '赵顾问', status: '跟进中' },
  { id: 3, customerName: '刘德华', phone: '177****2345', consultingDate: '2026-05-08', followPerson: '李经理', status: '待跟进' },
  { id: 4, customerName: '陈美玲', phone: '186****4567', consultingDate: '2026-05-07', followPerson: '孙顾问', status: '待跟进' },
  { id: 5, customerName: '杨光明', phone: '133****7890', consultingDate: '2026-05-06', followPerson: '赵顾问', status: '跟进中' },
];

// 近期入住记录
export interface RecentCheckin {
  id: number;
  customerName: string;
  roomNo: string;
  projectName: string;
  checkinDate: string;
  status: string;
}

export const recentCheckinList: RecentCheckin[] = [
  { id: 1, customerName: '周国强', roomNo: 'A-301', projectName: '阳光养老院', checkinDate: '2026-05-12', status: '已入住' },
  { id: 2, customerName: '吴桂香', roomNo: 'B-205', projectName: '夕阳红公寓', checkinDate: '2026-05-11', status: '已入住' },
  { id: 3, customerName: '郑伟民', roomNo: 'A-102', projectName: '阳光养老院', checkinDate: '2026-05-10', status: '已入住' },
  { id: 4, customerName: '冯秀兰', roomNo: 'C-408', projectName: '康寿老年中心', checkinDate: '2026-05-09', status: '已入住' },
  { id: 5, customerName: '何建国', roomNo: 'B-306', projectName: '夕阳红公寓', checkinDate: '2026-05-08', status: '已入住' },
];

// 快捷入口
export interface QuickEntry {
  title: string;
  icon: string;
  color: string;
  path?: string;
}

export const quickEntries: QuickEntry[] = [
  { title: '入住登记', icon: 'ant-design:form-outlined', color: '#1890ff', path: '/mvp/ElderCustomerCheckinList' },
  { title: '房间状态', icon: 'ant-design:appstore-outlined', color: '#52c41a', path: '/mvp/RoomStatusChat' },
  { title: '客户管理', icon: 'ant-design:user-outlined', color: '#722ed1', path: '/mvp/ElderCustomerList' },
  { title: '咨询管理', icon: 'ant-design:phone-outlined', color: '#13c2c2', path: '/mvp/ElderConsultingList' },
  { title: '缴费管理', icon: 'ant-design:dollar-outlined', color: '#fa8c16', path: '/mvp/ElderCustomerPaymentList' },
  { title: '账单管理', icon: 'ant-design:file-text-outlined', color: '#eb2f96', path: '/mvp/ElderBillList' },
  { title: '护理记录', icon: 'ant-design:medicine-box-outlined', color: '#2f54eb', path: '/mvp/ElderNursingRecordList' },
  { title: '能力评估', icon: 'ant-design:idcard-outlined', color: '#a0d911', path: '/mvp/ElderAssessmentList' },
];
