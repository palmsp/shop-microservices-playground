package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return order."

    request {
        url "/api/v1/order/all?customerId=999000"
        method GET()
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
                        [
                                id            : 1,
                                customerId    : 999000,
                                orderStatus   : "NEW",
                                orderPositions: [
                                        [
                                                id       : 2,
                                                orderId  : 1,
                                                productId: 111
                                        ],
                                        [
                                                id       : 3,
                                                orderId  : 1,
                                                productId: 222
                                        ]
                                ]
                        ], [
                                id            : 2,
                                customerId    : 999000,
                                orderStatus   : "NEW",
                                orderPositions: [
                                        [
                                                id       : 2,
                                                orderId  : 2,
                                                productId: 111
                                        ],
                                        [
                                                id       : 3,
                                                orderId  : 2,
                                                productId: 222
                                        ]
                                ]
                        ]
                ]
        )
    }
}
