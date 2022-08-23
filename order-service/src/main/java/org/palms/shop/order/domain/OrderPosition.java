package org.palms.shop.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_positions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_external_id")
    private Long productExternalId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "order_id")
    private Long orderId;

    /*
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;
     */
}
