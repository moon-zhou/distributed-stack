<template>
  <section class="login-page">
    <div class="login-card">
      <p class="shell__eyebrow">Vue 3 + Spring Boot</p>
      <h1>OpenSpec 前后端分离示例</h1>
      <p class="login-copy">
        使用当前后端的 JWT 登录接口完成认证，并进入用户和订单演示页面。
      </p>

      <form class="stack-form" @submit.prevent="submitLogin">
        <label>
          <span>用户名</span>
          <input v-model="form.username" type="text" required />
        </label>

        <label>
          <span>密码</span>
          <input v-model="form.password" type="password" required />
        </label>

        <button class="primary-button" type="submit" :disabled="loading">
          {{ loading ? '登录中...' : '登录并进入控制台' }}
        </button>
      </form>

      <p class="status-line">演示账号: admin / password</p>
      <p v-if="errorMessage" class="status-line status-line--error">{{ errorMessage }}</p>
    </div>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../services/auth'
import { sessionStore } from '../stores/session'

const router = useRouter()
const loading = ref(false)
const errorMessage = ref('')

const form = reactive({
  username: 'admin',
  password: 'password'
})

async function submitLogin() {
  loading.value = true
  errorMessage.value = ''

  try {
    const response = await login(form)
    sessionStore.setToken(response.token)
    router.push({ name: 'dashboard' })
  } catch (error) {
    errorMessage.value = error?.body?.message || error?.message || '登录失败，请检查后端是否启动。'
  } finally {
    loading.value = false
  }
}
</script>