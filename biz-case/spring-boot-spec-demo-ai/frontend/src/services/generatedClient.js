import { AuthService, OpenAPI, OrdersService, UsersService } from '../generated'
import { sessionStore } from '../stores/session'

OpenAPI.BASE = ''
OpenAPI.TOKEN = async () => sessionStore.token || ''

export { AuthService, OrdersService, UsersService }