import { AuthService } from './generatedClient'

export async function login(payload) {
  return await AuthService.login(payload)
}