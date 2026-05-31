import { beforeEach, describe, expect, it } from 'vitest'
import { OpenAPI } from '../generated'
import { sessionStore } from '../stores/session'
import './generatedClient'

describe('generatedClient configuration', () => {
  beforeEach(() => {
    sessionStore.clear()
  })

  it('uses relative base path for Vite proxy requests', () => {
    expect(OpenAPI.BASE).toBe('')
  })

  it('resolves bearer token from session store', async () => {
    sessionStore.setToken('test-token')

    const token = await OpenAPI.TOKEN()

    expect(token).toBe('test-token')
  })
})