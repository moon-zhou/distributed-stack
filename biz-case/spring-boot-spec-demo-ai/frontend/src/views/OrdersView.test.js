import { beforeEach, describe, expect, it, vi } from 'vitest'
import { mount } from '@vue/test-utils'

const fetchOrdersMock = vi.fn()
const createOrderMock = vi.fn()

vi.mock('../services/orders', () => ({
  fetchOrders: (...args) => fetchOrdersMock(...args),
  createOrder: (...args) => createOrderMock(...args)
}))

import OrdersView from './OrdersView.vue'

function flushPromises() {
  return new Promise((resolve) => {
    setTimeout(resolve, 0)
  })
}

function mountView() {
  return mount(OrdersView, {
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

describe('OrdersView', () => {
  beforeEach(() => {
    fetchOrdersMock.mockReset()
    createOrderMock.mockReset()

    fetchOrdersMock.mockResolvedValue([
      {
        id: 1,
        userId: 1,
        status: 'CREATED',
        totalAmount: 299,
        items: [
          {
            productId: 1,
            productName: 'Keyboard',
            quantity: 1,
            lineAmount: 199
          }
        ]
      }
    ])
  })

  it('loads and renders orders on mount', async () => {
    const wrapper = mountView()
    await flushPromises()

    expect(fetchOrdersMock).toHaveBeenCalledTimes(1)
    expect(wrapper.text()).toContain('#1')
    expect(wrapper.text()).toContain('CREATED')
  })

  it('adds line item row and submits order payload', async () => {
    createOrderMock.mockResolvedValue({ id: 2 })

    const wrapper = mountView()
    await flushPromises()

    const addLineItemButton = wrapper.findAll('button').find((btn) => btn.text() === '新增商品行')
    await addLineItemButton.trigger('click')

    const qtyInputs = wrapper.findAll('input[type="number"]')
    await qtyInputs[0].setValue(2)
    await qtyInputs[2].setValue(3)

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(createOrderMock).toHaveBeenCalledWith({
      userId: 2,
      items: [
        { productId: 1, quantity: 1 },
        { productId: 1, quantity: 3 }
      ]
    })
    expect(wrapper.text()).toContain('订单创建成功。')
    expect(fetchOrdersMock).toHaveBeenCalledTimes(2)
  })
})