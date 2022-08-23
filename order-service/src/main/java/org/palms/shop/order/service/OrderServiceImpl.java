package org.palms.shop.order.service;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.palms.shop.order.domain.Order;
import org.palms.shop.order.domain.OrderPosition;
import org.palms.shop.order.dto.OrderDto;
import org.palms.shop.order.dto.OrderPlacement;
import org.palms.shop.order.dto.OrderPositionDto;
import org.palms.shop.order.dto.OrderStatus;
import org.palms.shop.order.exception.NotFoundException;
import org.palms.shop.order.repository.OrderPositionRepository;
import org.palms.shop.order.repository.OrderRepository;
import org.palms.shop.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_NOT_FOUND = "Order with id=% not found";

    private OrderRepository orderRepository;
    private OrderPositionRepository orderPositionRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
            OrderPositionRepository orderPositionRepository) {
        this.orderRepository = orderRepository;
        this.orderPositionRepository = orderPositionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public OrderDto placeOrder(OrderPlacement orderPlacement) {
        Order orderToCreate = Order.builder()
                .customerId(orderPlacement.getCustomerId())
                .creationDate(LocalDate.now())
                .orderStatusId(OrderStatus.NEW.getStatusId())
                .build();
        Order createdOrder = orderRepository.save(orderToCreate);

        List<OrderPosition> orderPositionsToCreate = orderPlacement.getOrderPositions().stream()
                .map(orderPositionPlacement ->
                        OrderPosition.builder()
                                .orderId(createdOrder.getId())
                                .productExternalId(orderPositionPlacement.getProductId())
                                .build())
                .collect(Collectors.toList());
        //later products also will be booked by id in inventory-service
        List<OrderPosition> createdOrderPositions = orderPositionRepository.saveAll(orderPositionsToCreate);

        List<OrderPositionDto> orderPositions = createdOrderPositions.stream()
                .map(OrderPositionDto::from)
                .collect(Collectors.toList());

        OrderDto orderDto = OrderDto.from(createdOrder);
        orderDto.setOrderPositions(orderPositions);
        return orderDto;
    }

    /**
     * {@inheritDoc}
     */
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format(ORDER_NOT_FOUND, orderId)));
        List<OrderPosition> orderPositionEntities = orderPositionRepository.findAllByOrderId(orderId);

        List<OrderPositionDto> orderPositions = orderPositionEntities.stream()
                .map(OrderPositionDto::from)
                .collect(Collectors.toList());
        OrderDto orderDto = OrderDto.from(order);
        orderDto.setOrderPositions(orderPositions);

        return orderDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<OrderDto> getCustomerOrders(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if (isNotEmpty(orders)) {
            Map<Long, List<OrderPosition>> positionsForOrders = new HashMap<>();
            for (Order order : orders) {
                positionsForOrders.put(order.getId(), orderPositionRepository.findAllByOrderId(order.getId()));
            }
            List<OrderDto> orderDtos = orders.stream().map(OrderDto::from).collect(Collectors.toList());
            orderDtos.forEach(orderDto -> {
                orderDto.setOrderPositions(positionsForOrders.get(orderDto.getId())
                        .stream().map(OrderPositionDto::from)
                        .collect(Collectors.toList()));
            });
            return orderDtos;
        }
        return Collections.emptyList();
    }
}
