<template>
  <div class="customer-detail-page">
    <!-- 基本信息头部 -->
    <div class="detail-header">
      <div class="header-inner">
        <div class="header-left">
          <div class="customer-avatar">
            <a-avatar :size="66" style="background: #1890ff; font-size: 30px; font-weight: 600">
              {{ customerInfo.name?.charAt(0) }}
            </a-avatar>
          </div>
          <div class="customer-info">
            <div class="info-row name-row">
              <span class="info-name">{{ customerInfo.name }}</span>
              <a-tag :color="getStatusColor(customerInfo.status)" class="name-tag" v-if="customerInfo.status">
                {{ customerInfo.status }}
              </a-tag>
            </div>
            <div class="info-row detail-row">
              <span class="detail-item">
                <span class="detail-label">项目</span>
                <span class="detail-value">{{ customerInfo.projectName }}</span>
              </span>
              <span class="detail-item">
                <span class="detail-label">性别</span>
                <span class="detail-value">{{ getRenderDict(customerInfo.gender,'gender_type') }}</span>
              </span>
              <span class="detail-item">
                <span class="detail-label">年龄</span>
                <span class="detail-value">{{ calculateAge(customerInfo.birthDate) }}岁</span>
              </span>
              <span class="detail-item">
                <span class="detail-label">生日</span>
                <span class="detail-value">{{ customerInfo.birthDate }}</span>
              </span>
              <span class="detail-item">
                <span class="detail-label">证件号</span>
                <span class="detail-value">{{ customerInfo.idCard }}</span>
              </span> 
              <span class="detail-item">
                <span class="detail-label">电话</span>
                <span class="detail-value">{{ customerInfo.phone }}</span>
              </span>            
            </div>
            <div class="info-row detail-row">
              <span class="detail-item">
                <span class="detail-label">地址</span>
                <span class="detail-value">{{ customerInfo.address }}</span>
              </span>             
              <span class="detail-item">
                <span class="detail-label">备注</span>
                <span class="detail-value">{{ customerInfo.remark }}</span>
              </span>              
            </div>
          </div>
        </div>
        <div class="header-right" v-if="customerInfo.roomNo">
          <div class="room-card">
            <div class="room-label">当前房间</div>
            <div class="room-value">{{ customerInfo.buildingName }} /{{ customerInfo.floorName }} /{{ customerInfo.roomNo }}</div>
            <div class="room-bed">{{ customerInfo.bedNo }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Tab 内容区 -->
    <a-tabs v-model:activeKey="activeTab" class="detail-tabs" type="card" destroyInactiveTabPane>
      <a-tab-pane key="family" tab="家属列表">
        <div class="tab-content">
          <ElderCustomerFamilyList/>
        </div>
      </a-tab-pane>
      <a-tab-pane key="reserve" tab="预定记录">
        <div class="tab-content">
          <ElderRoomReserveList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="checkin" tab="入住记录">
        <div class="tab-content">
          <ElderCustomerCheckinList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="account" tab="账户">
        <div class="tab-content">
          <div class="account-overview">
            <div class="account-card">
              <div class="account-label">账户余额</div>
              <div class="account-amount">¥{{ accountInfo.balance }}</div>
            </div>
            <div class="account-card">
              <div class="account-label">押金</div>
              <div class="account-amount">¥{{ accountInfo.deposit }}</div>
            </div>
            <div class="account-card">
              <div class="account-label">累计充值</div>
              <div class="account-amount">¥{{ accountInfo.totalRecharge }}</div>
            </div>
          </div>
          <a-divider orientation="left">充值记录</a-divider>
          <ElderCustomerAccountList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="bill" tab="账单">
        <div class="tab-content">
          <ElderBillList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="outing" tab="外出记录">
        <div class="tab-content">
          <ElderCustomerOutingRecordList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="visit" tab="访问记录">
        <div class="tab-content">
          <ElderCustomerVisitingRecordList />
        </div>
      </a-tab-pane>

      <a-tab-pane key="care" tab="护理记录">
        <div class="tab-content">
          <ElderNursingRecordList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="history" tab="入住变更历史">
        <div class="tab-content">
          <ElderResidenceHistoryList/>
        </div>
      </a-tab-pane>

      <a-tab-pane key="vitals" tab="生命体征记录">
        <div class="tab-content">
          <ElderVitalSignsList />
        </div>
      </a-tab-pane>

      <a-tab-pane key="assessment" tab="能力评估">
        <div class="tab-content">
          <ElderAssessmentList/>
        </div>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import {getCustomerLastInfoMethod} from './../ElderCustomer.api';
import { useRoute } from 'vue-router'
import { render } from '/@/utils/common/renderUtils';
import ElderRoomReserveList from './../ElderRoomReserveList.vue';
import ElderCustomerCheckinList from './../ElderCustomerCheckinList.vue';
import ElderCustomerAccountList from './../ElderCustomerAccountList.vue';
import ElderBillList from './../ElderBillList.vue';
import ElderCustomerOutingRecordList from './../ElderCustomerOutingRecordList.vue';
import ElderCustomerVisitingRecordList from './../ElderCustomerVisitingRecordList.vue';
import ElderResidenceHistoryList from './../ElderResidenceHistoryList.vue';
import ElderNursingRecordList from './../ElderNursingRecordList.vue';
import ElderVitalSignsList from './../ElderVitalSignsList.vue';
import ElderAssessmentList from './../ElderAssessmentList.vue';
import ElderCustomerFamilyList from './../ElderCustomerFamilyList.vue';

const route = useRoute()
const activeTab = ref('family');
// 客户基本信息
const customerInfo = ref({});
onMounted(async () => {
  let dataResult = await getCustomerLastInfoMethod(route.query);
  if (dataResult && dataResult.checkinStatus && dataResult.checkinStatus == '1'){
    dataResult.status = '在住'
  } else if (dataResult && dataResult.reserveStatus && dataResult.reserveStatus == '1'){
    dataResult.status = '预定'
  }
  customerInfo.value = dataResult;
});

function getStatusColor(status: string) {
  const map: Record<string, string> = {
    '在住': 'processing',
    '预定': 'warning',
    '退住': 'default',
    '外出': 'cyan',
  };
  return map[status] || 'default';
}
function getRenderDict( text , dictCode){
  let te = render.renderDict(text, dictCode);
  return te.children;
}

/**
 * 计算周岁年龄
 * @param {string|Date} birthday - 生日日期（如 '1990-05-20' 或 Date 对象）
 * @returns {number} - 周岁年龄
 */
function calculateAge(birthday) {
    const birthDate = new Date(birthday);
    const today = new Date();
    
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();
    const dayDiff = today.getDate() - birthDate.getDate();
    
    // 如果今年生日还没到，年龄减1
    if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
        age--;
    }
    
    return age;
}


// 账户
const accountInfo = reactive({
  balance: 8500.00,
  deposit: 5000.00,
  totalRecharge: 35000.00,
});

</script>

<style lang="less" scoped>
.customer-detail-page {
  padding: 16px;
  background: #f5f7fa;
  min-height: 100vh;
}

/* 头部基本信息 */
.detail-header {
  background: #fff;
  border-radius: 8px;
  padding: 16px 20px;
  margin-bottom: 16px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);
}

.header-inner {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-left {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  flex: 1;
  min-width: 0;
}

.customer-avatar {
  flex-shrink: 0;
  padding-top: 2px;
}

.customer-info {
  flex: 1;
  min-width: 0;

  .info-row {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 4px 8px;
    margin-bottom: 4px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .info-name {
    font-size: 20px;
    font-weight: 700;
    color: #262626;
  }

  .name-tag {
    font-size: 14px;
    padding: 0 6px;
    height: 20px;
    line-height: 20px;
  }

  .meta-row {
    font-size: 15px;
    color: #595959;

    .meta-item {
      white-space: nowrap;
    }

    :deep(.ant-divider-vertical) {
      margin: 0 8px;
    }
  }

  .detail-row {
    .detail-item {
      display: inline-flex;
      align-items: center;
      gap: 10px;
      white-space: nowrap;

      .detail-label {
        font-size: 15px;
        color: #8c8c8c;
      }

      .detail-value {
        font-size: 15px;
        color: #262626;
      }

      .inline-tag {
        font-size: 14px;
        padding: 0 6px;
        height: 18px;
        line-height: 18px;
      }
    }
  }
}

.header-right {
  flex-shrink: 0;
}

.room-card {
  text-align: center;
  padding: 10px 20px;
  background: #f0f5ff;
  border-radius: 8px;
  border: 1px solid #d6e4ff;

  .room-label {
    font-size: 14px;
    color: #8c8c8c;
    margin-bottom: 2px;
  }

  .room-value {
    font-size: 18px;
    font-weight: 700;
    color: #1890ff;
  }

  .room-bed {
    font-size: 14px;
    color: #595959;
  }
}

/* Tab 区域 */
.detail-tabs {
  background: #fff;
  border-radius: 8px;
  padding: 16px 24px 24px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);

  :deep(.ant-tabs-nav) {
    margin-bottom: 16px;
  }

  :deep(.ant-tabs-tab) {
    font-size: 16px;
  }
}

.tab-content {
  min-height: 200px;
}

/* 账户概览 */
.account-overview {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  margin-bottom: 16px;

  .account-card {
    padding: 20px;
    border-radius: 8px;
    background: #f8f9fa;
    text-align: center;

    .account-label {
      font-size: 13px;
      color: #8c8c8c;
      margin-bottom: 8px;
    }

    .account-amount {
      font-size: 28px;
      font-weight: 700;
      color: #1890ff;
    }
  }
}

/* 能力评估概览 */
.assessment-overview {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: #f8f9fa;
  border-radius: 8px;
  margin-bottom: 16px;

  .assessment-score {
    display: flex;
    align-items: center;
    gap: 12px;

    .score-label {
      font-size: 14px;
      color: #595959;
    }

    .score-value {
      font-size: 36px;
      font-weight: 800;
      color: #1890ff;
    }

    .score-level {
      :deep(.ant-tag) {
        font-size: 14px;
        padding: 2px 12px;
      }
    }
  }

  .assessment-date {
    font-size: 13px;
    color: #8c8c8c;
  }
}

/* 响应式 */
@media (max-width: 768px) {
  .header-inner {
    flex-direction: column;
    align-items: flex-start;
  }

  .header-left {
    width: 100%;
  }

  .room-card {
    align-self: stretch;
    width: 100%;
    padding: 8px 16px;
  }

  .account-overview {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .customer-info .detail-row .detail-item {
    width: 100%;
  }
}
</style>
