package org.palms.shop.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.palms.shop.order.domain.Order;
import org.palms.shop.order.domain.OrderPosition;
import org.palms.shop.order.dto.OrderDto;
import org.palms.shop.order.dto.OrderPlacement;
import org.palms.shop.order.dto.OrderPositionDto;
import org.palms.shop.order.dto.OrderPositionPlacement;
import org.palms.shop.order.dto.OrderStatus;
import org.palms.shop.order.repository.OrderPositionRepository;
import org.palms.shop.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderPositionRepository orderPositionRepository;

    @Test
    void shouldPlaceOrder() {
        //given
        OrderPositionPlacement orderPositionPlacement = new OrderPositionPlacement();
        orderPositionPlacement.setProductId(RandomUtils.nextLong());

        OrderPlacement orderPlacement = new OrderPlacement();
        orderPlacement.setCustomerId(RandomUtils.nextLong());
        orderPlacement.setOrderPositions(List.of(orderPositionPlacement));

        Order expectedOrder = createdOrder(orderPlacement.getCustomerId());

        OrderPosition expectedOrderPosition = OrderPosition.builder()
                .productExternalId(orderPositionPlacement.getProductId())
                .orderId(expectedOrder.getId())
                .id(100L)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);
        when(orderPositionRepository.saveAll(any(List.class))).thenReturn(List.of(expectedOrderPosition));

        //when
        OrderDto orderDto = orderService.placeOrder(orderPlacement);

        //then
        assertEquals(expectedOrder.getId(), orderDto.getId());
        assertEquals(expectedOrder.getCustomerId(), orderDto.getCustomerId());
        assertEquals(expectedOrder.getOrderStatusId(), orderDto.getOrderStatus().getStatusId());
        assertEquals(expectedOrder.getCreationDate(), orderDto.getCreationDate());

        OrderPositionDto orderPositionDto = orderDto.getOrderPositions().get(0);
        assertEquals(orderPositionDto.getId(), expectedOrderPosition.getId());
        assertEquals(orderPositionDto.getOrderId(), expectedOrderPosition.getOrderId());
        assertEquals(orderPositionDto.getProductId(), expectedOrderPosition.getProductExternalId());
    }

    @Test
    void shouldFindOrderWithOrderPositions() {
        //given
        Long orderId = 999000L;
        Order expectedOrder = createdOrder(RandomUtils.nextLong());
        OrderPosition expectedOrderPosition = createOrderPosition(expectedOrder.getId());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));
        when(orderPositionRepository.findAllByOrderId(orderId)).thenReturn(List.of(expectedOrderPosition));

        //when
        OrderDto orderDto = orderService.getOrder(orderId);

        //then
        assertEquals(expectedOrder.getId(), orderDto.getId());
        assertEquals(expectedOrder.getCustomerId(), orderDto.getCustomerId());
        assertEquals(expectedOrder.getOrderStatusId(), orderDto.getOrderStatus().getStatusId());
        assertEquals(expectedOrder.getCreationDate(), orderDto.getCreationDate());

        OrderPositionDto orderPositionDto = orderDto.getOrderPositions().get(0);
        assertEquals(orderPositionDto.getId(), expectedOrderPosition.getId());
        assertEquals(orderPositionDto.getOrderId(), expectedOrderPosition.getOrderId());
        assertEquals(orderPositionDto.getProductId(), expectedOrderPosition.getProductExternalId());
    }

    @Test
    void shouldFindCustomerOrders() {
        //given
        Long customerId = 100L;
        Order expectedOrder1 = createdOrder(customerId);
        Order expectedOrder2 = createdOrder(customerId);
        when(orderRepository.findAllByCustomerId(customerId)).thenReturn(List.of(expectedOrder1, expectedOrder2));

        OrderPosition orderPosition1 = createOrderPosition(expectedOrder1.getId());
        OrderPosition orderPosition2 = createOrderPosition(expectedOrder2.getId());
        when(orderPositionRepository.findAllByOrderId(expectedOrder1.getId())).thenReturn(List.of(orderPosition1));
        when(orderPositionRepository.findAllByOrderId(expectedOrder2.getId())).thenReturn(List.of(orderPosition2));

        //when
        List<OrderDto> orderDtos = orderService.getCustomerOrders(customerId);

        //then
        OrderDto orderDto1 = orderDtos.get(0);
        OrderDto orderDto2 = orderDtos.get(1);
        assertEquals(expectedOrder1.getId(), orderDto1.getId());
        assertEquals(expectedOrder2.getId(), orderDto2.getId());
        assertEquals(expectedOrder1.getId(), orderDto1.getOrderPositions().get(0).getOrderId());
        assertEquals(expectedOrder2.getId(), orderDto2.getOrderPositions().get(0).getOrderId());
    }

    private Order createdOrder(Long customerId) {
        return Order.builder()
                .id(RandomUtils.nextLong())
                .customerId(customerId)
                .orderStatusId(OrderStatus.NEW.getStatusId())
                .creationDate(LocalDate.now())
                .build();
    }

    private OrderPosition createOrderPosition(Long orderId) {
        return OrderPosition.builder()
                .id(RandomUtils.nextLong())
                .orderId(orderId)
                .productExternalId(RandomUtils.nextLong())
                .build();
    }
}