<template>
  <div class="p-2 room-status-page">
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form ref="formRef" @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="6">
            <a-form-item name="projectId">
              <template #label><span title="所属项目">所属项目</span></template>
              <a-select
                v-model:value="queryParam.projectId"
                :options="projectList"
                :fieldNames="{ label: 'projectName', value: 'id' }"
                showSearch
                placeholder="请选择所属项目"
                @change="handleChangeProjectId"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="buildingId">
              <template #label><span title="楼栋">楼栋</span></template>
              <a-select
                v-model:value="queryParam.buildingId"
                :options="buildingList"
                :fieldNames="{ label: 'buildingName', value: 'id' }"
                showSearch
                placeholder="请选择楼栋"
                @change="handleChangeBuilding"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="floorId">
              <template #label><span title="楼层">楼层</span></template>
              <a-select
                v-model:value="queryParam.floorId"
                :options="floorList"
                :fieldNames="{ label: 'floorName', value: 'id' }"
                showSearch
                placeholder="请选择楼层"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="roomNo">
              <template #label><span title="房间号">房间号</span></template>
              <a-input placeholder="请输入房间号" v-model:value="queryParam.roomNo" allow-clear></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="customerName">
              <template #label><span title="客户名称">客户名称</span></template>
              <a-input placeholder="请输入客户名称" v-model:value="queryParam.customerName" allow-clear></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--楼层分组-->
    <div class="floor-groups">
      <!-- 空状态 -->
      <div v-if="!floorDataList || floorDataList.length === 0" class="empty-state">
        <div class="empty-icon">
          <Icon icon="ant-design:home-outlined" />
        </div>
        <div class="empty-title">暂无房间数据</div>
        <div class="empty-desc">请调整查询条件或添加房间信息</div>
      </div>
      <div v-for="floor in floorDataList" :key="floor.floorId" class="floor-section">
        <div class="floor-header">
          <div class="floor-title">{{ floor.projectName }}-{{floor.buildingName}}-{{ floor.floorName }}</div>
        </div>
        <div class="room-cards">
          <div
            v-for="room in floor.rooms"
            :key="room.roomId"
            class="room-card"
            :class="room.statusText"
            @click="handleRoomClick(room)"
          >
            <div class="room-card-header">
              <span class="room-no">{{ room.roomNo }}</span>
              <span class="room-status-tag" :class="room.statusText">{{ getStatusText(room.statusText) }}</span>
            </div>
            <div class="room-card-body">
              <div class="room-type">{{ getRenderDict(room.roomType) }}</div>
              <div class="bed-info">
                <div v-for="bed in room.beds" :key="bed.bedId" class="bed-item" :class="bed.statusText">
                  <span class="bed-icon" :class="bed.statusText">
                    <Icon icon="ant-design:bed-outline" />
                  </span>
                  <span class="bed-label">{{ bed.bedNo }}</span>
                  <span v-if="bed.customerName" class="bed-elder" @click="goCustomerPage(bed,floor.projectId)">
                    <span class="bed-elder-avatar">{{ bed.customerName.charAt(0) }}</span>
                    <span class="bed-elder-name">{{ bed.customerName }}</span>
                  </span>
                  <span class="bed-status" :class="bed.statusText">{{ getBedStatusText(bed.statusText) }}</span>
                  <span class="bed-price" v-if="bed.price">¥{{ bed.price }}/¥{{ bed.dprice }}</span>
                </div>
              </div>
            </div>
            <div class="room-card-actions">
              <template v-if="room.statusText === 'free' && room.beds">
                <a-button size="small" type="primary" @click.stop="handleCheckIn(room,floor.projectId)">入住</a-button>
                <a-button size="small" @click.stop="handleReserve(room,floor.projectId)">预定</a-button>
              </template>
              <template v-else-if="room.statusText === 'occupied' && room.beds">
                <a-button
                  v-if="room.beds.some(b => b.statusText == 'free' || b.statusText == 'reserved')"
                  size="small"
                  type="primary"
                  @click.stop="handleCheckIn(room,floor.projectId)"
                >入住</a-button>
                <a-button size="small" @click.stop="handleReserve(room,floor.projectId)" v-if="room.beds.some(b => b.statusText === 'free')">预定</a-button>
              </template>
              <template v-else-if="room.statusText === 'reserved' && room.beds">
                <a-button size="small" type="primary" @click.stop="handleCheckIn(room,floor.projectId)">入住</a-button>
                <a-button size="small" @click.stop="handleReserve(room,floor.projectId)" v-if="room.beds.some(b => b.statusText === 'free')">预定</a-button>
              </template>             
            </div>
            <div class="room-card-footer">
              <span class="area" v-if="room.area">{{ room.area }}㎡</span>
              <span class="bed-count" v-if="room.beds">{{ room.beds?.filter(b => b.statusText === 'occupied').length }} 住</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--分页-->
    <div class="pagination-bar" v-if="paginationInfo.total > 0">
      <a-pagination
        v-model:current="paginationInfo.pageNo"
        v-model:pageSize="paginationInfo.pageSize"
        :total="paginationInfo.total"
        :pageSizeOptions="['8', '12', '16', '24', '48']"
        showSizeChanger
        :show-total="(total, range) => `共 ${total} 条`"
        @change="handlePageChange"
      />
    </div>

     <!-- 预定 -->
     <ElderRoomReserveModal ref="registerReserveModal" @success="handleSuccess"></ElderRoomReserveModal>
     <!-- 表单区域 -->
    <ElderCustomerCheckinModal ref="registerCheckinModal" @success="handleSuccess"></ElderCustomerCheckinModal>
  </div>
</template>

<script lang="ts" name="com.joydigit.seniorcaring.mvp-RoomStatusChat" setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { Icon } from '/@/components/Icon';
import { useUserStore } from '/@/store/modules/user';
import { useMessage } from '/@/hooks/web/useMessage';
import { getBuildingListByProjectIdMethod } from './ElderBuilding.api';
import { getProjectListAllM } from './ElderProject.api';
import { getFloorListByBuildingIdMethod } from './ElderFloor.api';
import { getRoomStatusPageListMethod } from './ElderRoom.api';
import { Pagination } from 'ant-design-vue';
import { render } from '/@/utils/common/renderUtils';
import { useRouter } from 'vue-router';
import ElderRoomReserveModal from './components/ElderRoomReserveModal.vue';
import ElderCustomerCheckinModal from './components/ElderCustomerCheckinModal.vue';
const router = useRouter();

const APagination = Pagination;
const projectList = ref([]);
const buildingList = ref([]);
const floorList = ref([]);
const formRef = ref();
const queryParam = reactive<any>({});
const registerReserveModal = ref();
const registerCheckinModal = ref();
// 分页
const paginationInfo = reactive({
  pageNo: 1,
  pageSize: 12,
  total: 0,
});

// 房间状态字典
const statusMap = {
  free: { text: '空闲', color: '#52c41a' },
  occupied: { text: '已入住', color: '#1890ff' },
  reserved: { text: '预定', color: '#faad14' },
  maintenance: { text: '维修', color: '#f5222d' },
  cleaning: { text: '停用', color: '#722ed1' },
};

const bedStatusMap = {
  free: { text: '空床', color: '#52c41a' },
  occupied: { text: '已住', color: '#1890ff' },
  reserved: { text: '预定', color: '#faad14' },
};

function getStatusText(status: string) {
  return statusMap[status]?.text || status;
}
function getRenderDict( text ){
  let te = render.renderDict(text, 'room_type');
  return te.children;
}
function getBedStatusText(status: string) {
  return bedStatusMap[status]?.text || status;
}

// 根据床位状态聚合房间状态
function getRoomStatus(val) {
  if (val =='2') return 'occupied';
  if (val =='1') return 'reserved';
  if (val =='3') return 'maintenance';
  if (val =='4') return 'cleaning';
  return 'free';
}

const floorDataList = ref([]);


function handleRoomClick(room: any) {
  // console.log('点击房间:', room);
}

// 操作按钮
function handleCheckIn(room: any, projectId) {
  console.log('入住:', room.roomNo);
  registerCheckinModal.value.disableSubmit = false;
  const pardata = {
    projectId: projectId,
    roomId: room.roomId
  }
  registerCheckinModal.value.add(pardata);
}
function handleReserve(room: any, projectId) {
  registerReserveModal.value.disableSubmit = false;
  const pardata = {
    projectId: projectId,
    roomId: room.roomId
  }
  registerReserveModal.value.add(pardata);
}

function handleCancelReserve(room: any) {
  console.log('取消预定:', room.roomNo);
}

function handlePageChange(pageNumber) {
  paginationInfo.pageNo = pageNumber;
  searchQuery();
}

onMounted(async () => {
  projectList.value = await getProjectListAllM();
  await searchQuery();
});

  
/**
 * 成功回调
 */
function handleSuccess() {
  searchQuery();
}
   

// 查询楼栋
async function handleChangeProjectId(value) {
  if (value) {
    const data = await getBuildingListByProjectIdMethod({ projectId: value });
    buildingList.value = data;
  } else {
    buildingList.value = [];
  }
}
async function handleChangeBuilding(value) {
  if (value) {
    const data = await getFloorListByBuildingIdMethod({ buildingId: value });
    floorList.value = data;
  } else {
    floorList.value = [];
  }
}
const labelCol = reactive({
  xs: 24,
  sm: 4,
  xl: 6,
  xxl: 4,
});
const wrapperCol = reactive({
  xs: 24,
  sm: 20,
});

/**
 * 高级查询事件
 */
function handleSuperQuery(params) {
  Object.keys(params).map((k) => {
    queryParam[k] = params[k];
  });
  searchQuery();
}
function goCustomerPage(bed,projectId){
  router.push({ path: '/customer/detail', query: {id: bed.customerId,projectId: projectId} });
}
/**
 * 查询
 */
async function searchQuery() {
  queryParam.pageNo = paginationInfo.pageNo;
  queryParam.pageSize = paginationInfo.pageSize;
  let dataResult = await getRoomStatusPageListMethod(queryParam);
  dataResult.records.forEach(item => {
    item?.rooms.forEach(room => {
      room.statusText = getRoomStatus(room.status);
      room.beds?.forEach(bed =>{
        bed.statusText = getRoomStatus(bed.status);
      });
    })
  });
  floorDataList.value = dataResult.records;
  paginationInfo.total = dataResult.total;
}

/**
 * 重置
 */
function searchReset() {
  formRef.value.resetFields();
  paginationInfo.pageNo = 1;
  paginationInfo.pageSize = 12;
  searchQuery();
}
</script>

<style lang="less" scoped>
.room-status-page {
  background: #f5f7fa;
  min-height: 100vh;
}

.jeecg-basic-table-form-container {
  background: #fff;
  padding: 16px 24px;
  margin-bottom: 16px;
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);

  .table-page-search-submitButtons {
    display: block;
    margin-bottom: 24px;
    white-space: nowrap;
  }

  .query-group-cust {
    min-width: 100px !important;
  }

  .query-group-split-cust {
    width: 30px;
    display: inline-block;
    text-align: center;
  }

  .ant-form-item:not(.ant-form-item-with-help) {
    margin-bottom: 16px;
    height: 32px;
  }

  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }
}

/* 统计区域 */
.status-statistics {
  display: flex;
  flex-direction: column;
  align-items: left;
  gap: 12px;
  padding: 20px 24px;
  background: #fff;
  border-radius: 8px;
  margin-bottom: 16px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);

  /* 入住率大数字 */
  .stat-rate-box {
    text-align: center;

    .rate-big {
      font-size: 42px;
      font-weight: 800;
      color: #1890ff;
      line-height: 1;

      .rate-unit {
        font-size: 18px;
        font-weight: 500;
        color: #8c8c8c;
        margin-left: 2px;
      }
    }

    .rate-desc {
      font-size: 14px;
      color: #8c8c8c;
      margin-top: 4px;
    }
  }

  /* 分段进度条 */
  .stat-segment-bar {
    display: flex;
    width: 100%;
    max-width: 600px;
    height: 10px;
    border-radius: 5px;
    overflow: hidden;

    .segment {
      height: 100%;
      transition: width 0.5s ease;
      cursor: pointer;

      &.occupied { background: #1890ff; }
      &.free { background: #52c41a; }
      &.reserved { background: #faad14; }
      &.maintenance { background: #f5222d; }
      &.cleaning { background: #722ed1; }
    }
  }

  /* 图例 — 色块和文字合并 */
  .stat-legend {
    display: flex;
    flex-wrap: wrap;
    justify-content: left;
    gap: 8px;

    .legend-item {
      display: flex;
      align-items: left;
      gap: 6px;
      padding: 4px 12px;
      border-radius: 6px;
      font-size: 13px;

      .legend-block {
        font-weight: 700;
        font-size: 15px;
      }

      .legend-label {
        opacity: 0.85;
      }

      &.occupied {
        background: #e6f7ff;
        color: #1890ff;
      }

      &.free {
        background: #f6ffed;
        color: #52c41a;
      }

      &.reserved {
        background: #fffbe6;
        color: #faad14;
      }

      &.maintenance {
        background: #fff1f0;
        color: #f5222d;
      }

      &.cleaning {
        background: #f9f0ff;
        color: #722ed1;
      }
    }
  }

  /* 辅助指标 */
  .stat-aux {
    display: flex;
    gap: 24px;
    margin-top: 4px;

    .aux-item {
      display: flex;
      align-items: baseline;
      gap: 6px;

      .aux-num {
        font-size: 20px;
        font-weight: 700;
        color: #262626;
      }

      .aux-label {
        font-size: 13px;
        color: #8c8c8c;
      }
    }
  }
}

/* 楼层分组 */
.floor-groups {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* 空状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 24px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);

  .empty-icon {
    font-size: 64px;
    color: #d9d9d9;
    margin-bottom: 16px;
  }

  .empty-title {
    font-size: 16px;
    font-weight: 500;
    color: #595959;
    margin-bottom: 8px;
  }

  .empty-desc {
    font-size: 14px;
    color: #8c8c8c;
  }
}

.floor-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px 24px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);
}

.floor-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;

  .floor-title {
    font-size: 16px;
    font-weight: 600;
    color: #262626;
  }

  .floor-summary {
    display: flex;
    gap: 8px;

    .summary-tag {
      font-size: 12px;
      padding: 2px 10px;
      border-radius: 4px;
      font-weight: 500;

      &.total {
        background: #e6f7ff;
        color: #1890ff;
      }

      &.free {
        background: #f6ffed;
        color: #52c41a;
      }

      &.occupied {
        background: #e6f7ff;
        color: #1890ff;
      }
    }
  }
}

/* 房间卡片 */
.room-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.room-card {
  position: relative;
  border-radius: 10px;
  border: 2px solid #f0f0f0;
  padding: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
  background: #fff;
  display: flex;
  flex-direction: column;
  min-height: 200px;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  /* 状态边框色 */
  &.free {
    border-color: #b7eb8f;

    .room-status-tag {
      background: #f6ffed;
      color: #52c41a;
    }
  }

  &.occupied {
    border-color: #91d5ff;

    .room-status-tag {
      background: #e6f7ff;
      color: #1890ff;
    }
  }

  &.reserved {
    border-color: #ffe58f;

    .room-status-tag {
      background: #fffbe6;
      color: #faad14;
    }
  }

  &.maintenance {
    border-color: #ffa39e;

    .room-status-tag {
      background: #fff1f0;
      color: #f5222d;
    }
  }

  &.cleaning {
    border-color: #d3adf7;

    .room-status-tag {
      background: #f9f0ff;
      color: #722ed1;
    }
  }
}

.room-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;

  .room-no {
    font-size: 18px;
    font-weight: 700;
    color: #262626;
  }

  .room-status-tag {
    font-size: 12px;
    padding: 2px 8px;
    border-radius: 4px;
    font-weight: 500;
  }
}

.room-card-body {
  flex: 1;

  .room-type {
    font-size: 13px;
    color: #595959;
    margin-bottom: 10px;
  }

  .bed-info {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }

  .bed-item {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 4px 8px;
    border-radius: 6px;
    background: #f8f9fa;

    .bed-icon {
      font-size: 16px;

      &.free {
        color: #52c41a;
      }

      &.occupied {
        color: #1890ff;
      }

      &.reserved {
        color: #faad14;
      }
    }

    .bed-label {
      font-size: 12px;
      color: #595959;
      min-width: 28px;
    }

    .bed-status {
      font-size: 11px;
      padding: 0 5px;
      border-radius: 3px;
      font-weight: 500;

      &.free {
        color: #52c41a;
        background: #f6ffed;
      }

      &.occupied {
        color: #1890ff;
        background: #e6f7ff;
      }

      &.reserved {
        color: #faad14;
        background: #fffbe6;
      }
    }

    .bed-price {
      margin-left: auto;
      font-size: 12px;
      font-weight: 600;
      color: #f5222d;
    }
  }
}

/* 操作按钮 */
.room-card-actions {
  margin-top: auto;
  padding-top: 10px;
  display: flex;
  gap: 6px;
  flex-wrap: wrap;
}

.room-card-footer {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px dashed #e8e8e8;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .area {
    font-size: 12px;
    color: #8c8c8c;
  }

  .bed-count {
    font-size: 12px;
    color: #595959;
    font-weight: 500;
  }
}

    .bed-elder {
      display: flex;
      align-items: center;
      gap: 4px;
      margin-left: 2px;

      .bed-elder-avatar {
        width: 18px;
        height: 18px;
        border-radius: 50%;
        background: #1890ff;
        color: #fff;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 10px;
        font-weight: 600;
        flex-shrink: 0;
      }

      .bed-elder-name {
        font-size: 12px;
        color: #1890ff;
        font-weight: 500;
      }
    }

/* 分页 */
.pagination-bar {
  display: flex;
  justify-content: flex-end;
  padding: 16px 24px;
  background: #fff;
  border-radius: 8px;
  margin-top: 16px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);
}

/* 响应式 */
@media (max-width: 768px) {
  .room-cards {
    grid-template-columns: repeat(auto-fill, minmax(270px, 1fr));
  }

  .status-statistics {
    .stat-aux {
      gap: 16px;
    }
  }
}

@media (max-width: 480px) {
  .status-statistics {
    .stat-rate-box .rate-big {
      font-size: 32px;
    }

    .stat-aux {
      gap: 12px;

      .aux-item {
        flex-direction: column;
        align-items: center;
        gap: 2px;
      }
    }
  }
}
</style>
