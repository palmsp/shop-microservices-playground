package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should place order and return created one."

    request {
        url "/api/v1/order"
        method POST()
        body(
                customerId: 999000,
                orderPositions: [
                        [productId: 111],
                        [productId: 222]
                ],
        )
        headers {
            contentType applicationJson()
        }
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(
                       [
                                id: 1,
                                customerId: 999000,
                                orderStatus: "NEW",
                                orderPositions: [
                                        [
                                                id: 2,
                                                orderId: 1,
                                                productId: 111
                                        ],
                                        [
                                                id: 3,
                                                orderId: 1,
                                                productId: 222
                                        ]
                                ]
                        ]
        )
    }
}
