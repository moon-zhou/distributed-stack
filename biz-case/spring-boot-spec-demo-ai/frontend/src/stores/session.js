import { reactive } from 'vue'

const TOKEN_KEY = 'openspec-demo-token'

export const sessionStore = reactive({
  token: localStorage.getItem(TOKEN_KEY) || '',

  setToken(token) {
    this.token = token
    localStorage.setItem(TOKEN_KEY, token)
  },

  clear() {
    this.token = ''
    localStorage.removeItem(TOKEN_KEY)
  },

  hasToken() {
    return Boolean(this.token)
  }
})