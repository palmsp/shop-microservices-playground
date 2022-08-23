package org.palms.shop.order.factory;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.palms.shop.order.domain.Order;
import org.palms.shop.order.domain.OrderPosition;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntityFactory {


    public static Order.OrderBuilder orderBuilder() {
        return Order.builder()
                .customerId(RandomUtils.nextLong())
                .orderStatusId(RandomUtils.nextInt())
                .creationDate(LocalDate.now());
    }

    public static OrderPosition.OrderPositionBuilder orderPositionBuilder(Long orderId){
        return OrderPosition.builder()
                .orderId(orderId)
                .productName(RandomStringUtils.randomAscii(10))
                .productExternalId(RandomUtils.nextLong());
    }

}
