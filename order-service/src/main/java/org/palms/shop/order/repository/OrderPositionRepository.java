package org.palms.shop.order.repository;

import java.util.List;
import org.palms.shop.order.domain.Order;
import org.palms.shop.order.domain.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link OrderPosition}
 */
@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {

    /**
     * Find all order positions for specific order id.
     * @param orderId order id
     * @return list of order positions.
     */
    List<OrderPosition> findAllByOrderId(Long orderId);
}
