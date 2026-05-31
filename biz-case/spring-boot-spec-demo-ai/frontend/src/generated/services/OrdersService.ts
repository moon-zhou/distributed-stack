/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateOrderRequest } from '../models/CreateOrderRequest';
import type { OrderResponse } from '../models/OrderResponse';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class OrdersService {
    /**
     * Create order (multi-table operation)
     * @param requestBody
     * @returns OrderResponse Created
     * @throws ApiError
     */
    public static createOrder(
        requestBody: CreateOrderRequest,
    ): CancelablePromise<OrderResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/orders',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * List orders
     * @returns OrderResponse OK
     * @throws ApiError
     */
    public static listOrders(): CancelablePromise<Array<OrderResponse>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/orders',
        });
    }
    /**
     * Get order by id
     * @param id
     * @returns OrderResponse OK
     * @throws ApiError
     */
    public static getOrderById(
        id: number,
    ): CancelablePromise<OrderResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/orders/{id}',
            path: {
                'id': id,
            },
            errors: {
                404: `Not found`,
            },
        });
    }
}
