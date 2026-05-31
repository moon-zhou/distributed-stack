import { UsersService } from './generatedClient'

export async function fetchUsers() {
  return await UsersService.listUsers()
}

export async function createUser(payload) {
  return await UsersService.createUser(payload)
}

export async function updateUser(id, payload) {
  return await UsersService.updateUser(id, payload)
}

export async function deleteUser(id) {
  await UsersService.deleteUser(id)
}