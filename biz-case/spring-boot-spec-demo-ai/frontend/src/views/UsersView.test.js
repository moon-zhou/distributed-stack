import { beforeEach, describe, expect, it, vi } from 'vitest'
import { mount } from '@vue/test-utils'

const fetchUsersMock = vi.fn()
const createUserMock = vi.fn()
const updateUserMock = vi.fn()
const deleteUserMock = vi.fn()

vi.mock('../services/users', () => ({
  fetchUsers: (...args) => fetchUsersMock(...args),
  createUser: (...args) => createUserMock(...args),
  updateUser: (...args) => updateUserMock(...args),
  deleteUser: (...args) => deleteUserMock(...args)
}))

import UsersView from './UsersView.vue'

function flushPromises() {
  return new Promise((resolve) => {
    setTimeout(resolve, 0)
  })
}

function mountView() {
  return mount(UsersView, {
    global: {
      stubs: {
        AppShell: {
          props: ['title'],
          template: '<div><slot /></div>'
        }
      }
    }
  })
}

describe('UsersView', () => {
  beforeEach(() => {
    fetchUsersMock.mockReset()
    createUserMock.mockReset()
    updateUserMock.mockReset()
    deleteUserMock.mockReset()

    fetchUsersMock.mockResolvedValue([
      { id: 1, username: 'admin', email: 'admin@example.com' }
    ])
  })

  it('loads and renders users on mount', async () => {
    const wrapper = mountView()
    await flushPromises()

    expect(fetchUsersMock).toHaveBeenCalledTimes(1)
    expect(wrapper.text()).toContain('admin@example.com')
  })

  it('creates a user and shows success message', async () => {
    createUserMock.mockResolvedValue({ id: 2, username: 'tom', email: 'tom@example.com' })

    const wrapper = mountView()
    await flushPromises()

    const inputs = wrapper.findAll('input')
    await inputs[0].setValue('tom')
    await inputs[1].setValue('password123')
    await inputs[2].setValue('tom@example.com')

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(createUserMock).toHaveBeenCalledWith({
      username: 'tom',
      password: 'password123',
      email: 'tom@example.com'
    })
    expect(wrapper.text()).toContain('用户创建成功。')
    expect(fetchUsersMock).toHaveBeenCalledTimes(2)
  })

  it('deletes a user and refreshes list', async () => {
    deleteUserMock.mockResolvedValue(undefined)

    const wrapper = mountView()
    await flushPromises()

    const deleteButton = wrapper.find('.text-button--danger')
    await deleteButton.trigger('click')
    await flushPromises()

    expect(deleteUserMock).toHaveBeenCalledWith(1)
    expect(wrapper.text()).toContain('用户 1 已删除。')
    expect(fetchUsersMock).toHaveBeenCalledTimes(2)
  })
})