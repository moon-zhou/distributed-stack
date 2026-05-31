/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type OrderResponse = {
    id?: number;
    userId?: number;
    status?: string;
    totalAmount?: number;
    createdAt?: string;
    items?: Array<{
        productId?: number;
        productName?: string;
        quantity?: number;
        unitPrice?: number;
        lineAmount?: number;
    }>;
};

