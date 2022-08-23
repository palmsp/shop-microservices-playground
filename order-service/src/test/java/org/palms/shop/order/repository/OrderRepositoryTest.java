package org.palms.shop.order.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.palms.shop.order.domain.Order;
import org.palms.shop.order.domain.OrderPosition;
import org.palms.shop.order.factory.OrderEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPositionRepository orderPositionRepository;

    @Test
    public void shouldCreateNewOrderWithOrderPositions() {

        Order order = OrderEntityFactory.orderBuilder().build();
        Order savedOrder = orderRepository.save(order);

        OrderPosition orderPosition1 = OrderEntityFactory.orderPositionBuilder(savedOrder.getId()).build();
        OrderPosition orderPosition2 = OrderEntityFactory.orderPositionBuilder(savedOrder.getId()).build();
        //order position of other order
        OrderPosition orderPosition3 = OrderEntityFactory.orderPositionBuilder(RandomUtils.nextLong()).build();

        orderPositionRepository.saveAll(List.of(orderPosition1, orderPosition2, orderPosition3));

        Order resultOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        List<OrderPosition> resultOrderPosition = orderPositionRepository.findAllByOrderId(savedOrder.getId());

        assertThat(resultOrder).isNotNull();
        assertThat(resultOrderPosition).isNotNull();
        assertThat(resultOrderPosition.size()).isEqualTo(2);
    }

    @Test
    public void shouldUpdateExistingOrder() {
        Order savedOrder = createOrder();

        int statusId = 2;
        LocalDate deliveryDate = LocalDate.now().plusDays(2);
        Order updatedOrder = Order.builder()
                .id(savedOrder.getId())
                .creationDate(savedOrder.getCreationDate())
                .orderStatusId(statusId)
                .deliveryDate(deliveryDate)
                .build();

        Order result = orderRepository.save(updatedOrder);

        assertThat(result.getId()).isEqualTo(savedOrder.getId());
        assertThat(result.getDeliveryDate()).isEqualTo(updatedOrder.getDeliveryDate());
        assertThat(result.getOrderStatusId()).isEqualTo(updatedOrder.getOrderStatusId());
    }

    @Test
    public void shouldUpdateExistingOrderPosition() {
        Order savedOrder = createOrder();
        OrderPosition savedOrderPosition = createOrderPosition(savedOrder.getId());

        String productName = "Updated productName";
        OrderPosition updatedOrderPosition = OrderPosition.builder()
                .id(savedOrderPosition.getId())
                .orderId(savedOrderPosition.getOrderId())
                .productExternalId(savedOrderPosition.getProductExternalId())
                .productName(productName)
                .build();

        OrderPosition result = orderPositionRepository.save(updatedOrderPosition);

        assertThat(result.getId()).isEqualTo(savedOrderPosition.getId());
        assertThat(result.getProductName()).isEqualTo(updatedOrderPosition.getProductName());
    }

    @Test
    public void shouldFindAllOrdersForCustomerId() {
        Long customerId = 999000L;
        Order order1 = OrderEntityFactory.orderBuilder().customerId(customerId).build();
        Order order2 = OrderEntityFactory.orderBuilder().customerId(customerId).build();
        Order order3 = OrderEntityFactory.orderBuilder().customerId(customerId).build();
        //order of other customer
        Order order4 = OrderEntityFactory.orderBuilder().customerId(100L).build();

        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        List<Order> result = orderRepository.findAllByCustomerId(customerId);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
    }

    private Order createOrder() {
        Order order = OrderEntityFactory.orderBuilder().build();
        return orderRepository.save(order);
    }

    private OrderPosition createOrderPosition(Long orderId) {
        OrderPosition orderPosition = OrderEntityFactory.orderPositionBuilder(orderId).build();
        return orderPositionRepository.save(orderPosition);
    }
}
