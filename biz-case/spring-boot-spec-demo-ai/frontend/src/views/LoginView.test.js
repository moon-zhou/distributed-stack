import { beforeEach, describe, expect, it, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import { sessionStore } from '../stores/session'

const pushMock = vi.fn()
const loginMock = vi.fn()

vi.mock('vue-router', () => ({
  useRouter: () => ({
    push: pushMock
  })
}))

vi.mock('../services/auth', () => ({
  login: (...args) => loginMock(...args)
}))

import LoginView from './LoginView.vue'

function flushPromises() {
  return new Promise((resolve) => {
    setTimeout(resolve, 0)
  })
}

describe('LoginView', () => {
  beforeEach(() => {
    pushMock.mockReset()
    loginMock.mockReset()
    sessionStore.clear()
  })

  it('stores token and redirects after successful login', async () => {
    loginMock.mockResolvedValue({ token: 'jwt-token' })

    const wrapper = mount(LoginView)

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(loginMock).toHaveBeenCalledWith({ username: 'admin', password: 'password' })
    expect(sessionStore.token).toBe('jwt-token')
    expect(pushMock).toHaveBeenCalledWith({ name: 'dashboard' })
  })

  it('shows backend error message from generated ApiError body', async () => {
    loginMock.mockRejectedValue({
      body: { message: '账号或密码错误' },
      message: 'Unauthorized'
    })

    const wrapper = mount(LoginView)

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(wrapper.text()).toContain('账号或密码错误')
    expect(sessionStore.token).toBe('')
    expect(pushMock).not.toHaveBeenCalled()
  })
})