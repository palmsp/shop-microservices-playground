package org.palms.shop.order.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Keeps order statuses.
 */
public enum OrderStatus {

    NEW(10),
    PLACED(20),
    IN_ASSEMBLING(30),
    ASSEMBLED(40),
    IN_DELIVERY(50),
    DELIVERED(60),
    CANCELED(70);

    private static Map<Integer, OrderStatus> statusMap = Arrays.stream(values())
            .collect(Collectors.toMap(OrderStatus::getStatusId, Function.identity()));

    private Integer statusId;

    OrderStatus(int statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public static OrderStatus statusByid(Integer id) {
        return statusMap.get(id);
    }

}
