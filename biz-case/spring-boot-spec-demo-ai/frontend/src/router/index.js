import { createRouter, createWebHistory } from 'vue-router'
import { sessionStore } from '../stores/session'
import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import UsersView from '../views/UsersView.vue'
import OrdersView from '../views/OrdersView.vue'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { public: true }
  },
  {
    path: '/',
    name: 'dashboard',
    component: DashboardView
  },
  {
    path: '/users',
    name: 'users',
    component: UsersView
  },
  {
    path: '/orders',
    name: 'orders',
    component: OrdersView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  if (!to.meta.public && !sessionStore.hasToken()) {
    return { name: 'login' }
  }

  if (to.name === 'login' && sessionStore.hasToken()) {
    return { name: 'dashboard' }
  }

  return true
})

export default router