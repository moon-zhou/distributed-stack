<template>
  <AppShell title="系统概览">
    <section class="stats-grid">
      <article class="stat-card">
        <p>用户总数</p>
        <strong>{{ stats.userCount }}</strong>
      </article>
      <article class="stat-card">
        <p>订单总数</p>
        <strong>{{ stats.orderCount }}</strong>
      </article>
      <article class="stat-card stat-card--accent">
        <p>最近订单金额</p>
        <strong>{{ stats.latestAmount }}</strong>
      </article>
    </section>

    <section class="panel">
      <div class="panel__header">
        <h3>当前协作方式</h3>
      </div>
      <div class="info-grid">
        <div>
          <span class="info-label">契约源</span>
          <p>backend/src/main/resources/openapi/openapi.yaml</p>
        </div>
        <div>
          <span class="info-label">前端路由</span>
          <p>登录、用户管理、订单管理</p>
        </div>
        <div>
          <span class="info-label">认证机制</span>
          <p>JWT Bearer Token</p>
        </div>
      </div>
    </section>
  </AppShell>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import AppShell from '../components/AppShell.vue'
import { fetchUsers } from '../services/users'
import { fetchOrders } from '../services/orders'

const stats = reactive({
  userCount: 0,
  orderCount: 0,
  latestAmount: '0.00'
})

onMounted(async () => {
  const [users, orders] = await Promise.all([fetchUsers(), fetchOrders()])
  stats.userCount = users.length
  stats.orderCount = orders.length
  stats.latestAmount = orders[0]?.totalAmount?.toFixed?.(2) || `${orders[0]?.totalAmount ?? '0.00'}`
})
</script>