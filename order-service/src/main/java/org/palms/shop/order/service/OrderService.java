package org.palms.shop.order.service;

import java.util.List;
import org.palms.shop.order.dto.OrderDto;
import org.palms.shop.order.dto.OrderPlacement;

/**
 * Order service.
 */
public interface OrderService {

    /**
     * Place order.
     *
     * @param orderPlacement {@link OrderPlacement}
     * @return created order
     */
    OrderDto placeOrder(OrderPlacement orderPlacement);

    /**
     * Find order by id
     *
     * @param orderId order id.
     * @return order
     */
    public OrderDto getOrder(Long orderId);

    /**
     * Find all orders for specific customer.
     *
     * @param customerId customer id.
     * @return list of orders
     */
    public List<OrderDto> getCustomerOrders(Long customerId);
}
