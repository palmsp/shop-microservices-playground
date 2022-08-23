package org.palms.shop.order.repository;

import java.util.List;
import org.palms.shop.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Order}
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders with specific customer id.
     * @param customerId customer id
     * @return list of orders
     */
    List<Order> findAllByCustomerId(Long customerId);
}
