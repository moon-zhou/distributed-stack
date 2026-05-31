<template>
  <AppShell title="用户管理">
    <section class="content-grid">
      <article class="panel">
        <div class="panel__header">
          <h3>{{ editingId ? '编辑用户邮箱' : '创建用户' }}</h3>
        </div>

        <form class="stack-form" @submit.prevent="submitUser">
          <label>
            <span>用户名</span>
            <input v-model="form.username" type="text" :disabled="Boolean(editingId)" required />
          </label>

          <label>
            <span>密码</span>
            <input v-model="form.password" type="password" :disabled="Boolean(editingId)" :required="!editingId" />
          </label>

          <label>
            <span>邮箱</span>
            <input v-model="form.email" type="email" required />
          </label>

          <div class="button-row">
            <button class="primary-button" type="submit">{{ editingId ? '保存修改' : '创建用户' }}</button>
            <button v-if="editingId" class="ghost-button" type="button" @click="resetForm">取消编辑</button>
          </div>
        </form>
      </article>

      <article class="panel">
        <div class="panel__header">
          <h3>用户列表</h3>
          <button class="ghost-button" type="button" @click="loadUsers">刷新</button>
        </div>

        <table class="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>邮箱</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td class="table-actions">
                <button class="text-button" type="button" @click="startEdit(user)">编辑</button>
                <button class="text-button text-button--danger" type="button" @click="removeUser(user.id)">删除</button>
              </td>
            </tr>
          </tbody>
        </table>

        <p v-if="message" class="status-line">{{ message }}</p>
      </article>
    </section>
  </AppShell>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import AppShell from '../components/AppShell.vue'
import { createUser, deleteUser, fetchUsers, updateUser } from '../services/users'

const users = ref([])
const editingId = ref(null)
const message = ref('')
const form = reactive({
  username: '',
  password: '',
  email: ''
})

async function loadUsers() {
  users.value = await fetchUsers()
}

function resetForm() {
  editingId.value = null
  form.username = ''
  form.password = ''
  form.email = ''
}

function startEdit(user) {
  editingId.value = user.id
  form.username = user.username
  form.password = ''
  form.email = user.email
}

async function submitUser() {
  if (editingId.value) {
    await updateUser(editingId.value, { email: form.email })
    message.value = '用户邮箱已更新。'
  } else {
    await createUser({ ...form })
    message.value = '用户创建成功。'
  }

  resetForm()
  await loadUsers()
}

async function removeUser(id) {
  await deleteUser(id)
  message.value = `用户 ${id} 已删除。`
  await loadUsers()
}

onMounted(loadUsers)
</script>