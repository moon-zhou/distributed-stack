import { OrdersService } from './generatedClient'

export async function fetchOrders() {
  return await OrdersService.listOrders()
}

export async function createOrder(payload) {
  return await OrdersService.createOrder(payload)
}