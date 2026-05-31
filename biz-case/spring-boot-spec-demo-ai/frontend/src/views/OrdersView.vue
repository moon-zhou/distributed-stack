<template>
  <AppShell title="订单管理">
    <section class="content-grid">
      <article class="panel">
        <div class="panel__header">
          <h3>创建订单</h3>
        </div>

        <form class="stack-form" @submit.prevent="submitOrder">
          <label>
            <span>用户 ID</span>
            <input v-model.number="form.userId" type="number" min="1" required />
          </label>

          <div class="line-items">
            <div v-for="(item, index) in form.items" :key="index" class="line-item-card">
              <label>
                <span>商品</span>
                <select v-model.number="item.productId">
                  <option v-for="product in productCatalog" :key="product.id" :value="product.id">
                    {{ product.name }}
                  </option>
                </select>
              </label>

              <label>
                <span>数量</span>
                <input v-model.number="item.quantity" type="number" min="1" required />
              </label>
            </div>
          </div>

          <div class="button-row">
            <button class="ghost-button" type="button" @click="addLineItem">新增商品行</button>
            <button class="primary-button" type="submit">提交订单</button>
          </div>
        </form>
      </article>

      <article class="panel">
        <div class="panel__header">
          <h3>订单列表</h3>
          <button class="ghost-button" type="button" @click="loadOrders">刷新</button>
        </div>

        <div class="orders-stack">
          <article v-for="order in orders" :key="order.id" class="order-card">
            <div class="order-card__header">
              <strong>#{{ order.id }}</strong>
              <span>{{ order.status }}</span>
            </div>
            <p>用户 ID: {{ order.userId }}</p>
            <p>总金额: {{ order.totalAmount }}</p>
            <ul>
              <li v-for="item in order.items" :key="`${order.id}-${item.productId}`">
                {{ item.productName }} x {{ item.quantity }} = {{ item.lineAmount }}
              </li>
            </ul>
          </article>
        </div>

        <p v-if="message" class="status-line">{{ message }}</p>
      </article>
    </section>
  </AppShell>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import AppShell from '../components/AppShell.vue'
import { createOrder, fetchOrders } from '../services/orders'

const orders = ref([])
const message = ref('')
const productCatalog = [
  { id: 1, name: 'Keyboard' },
  { id: 2, name: 'Mouse' },
  { id: 3, name: 'Monitor' }
]

const form = reactive({
  userId: 1,
  items: [
    {
      productId: 1,
      quantity: 1
    }
  ]
})

function addLineItem() {
  form.items.push({ productId: 1, quantity: 1 })
}

async function loadOrders() {
  orders.value = await fetchOrders()
}

async function submitOrder() {
  await createOrder({
    userId: form.userId,
    items: form.items.map((item) => ({
      productId: item.productId,
      quantity: item.quantity
    }))
  })

  message.value = '订单创建成功。'
  await loadOrders()
}

onMounted(loadOrders)
</script>