<template>
  <div class="shell">
    <aside class="shell__sidebar">
      <div>
        <p class="shell__eyebrow">OpenSpec Demo</p>
        <h1>前后端分离控制台</h1>
      </div>
      <nav class="shell__nav">
        <RouterLink to="/">概览</RouterLink>
        <RouterLink to="/users">用户管理</RouterLink>
        <RouterLink to="/orders">订单管理</RouterLink>
      </nav>
      <button class="ghost-button" type="button" @click="logout">退出登录</button>
    </aside>

    <main class="shell__content">
      <header class="shell__header">
        <div>
          <p class="shell__eyebrow">API First Workspace</p>
          <h2>{{ title }}</h2>
        </div>
        <p class="shell__hint">后端地址: http://localhost:8080</p>
      </header>
      <slot />
    </main>
  </div>
</template>

<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { sessionStore } from '../stores/session'

const props = defineProps({
  title: {
    type: String,
    required: true
  }
})

const router = useRouter()

function logout() {
  sessionStore.clear()
  router.push({ name: 'login' })
}
</script>