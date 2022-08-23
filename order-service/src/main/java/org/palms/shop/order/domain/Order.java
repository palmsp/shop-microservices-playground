package org.palms.shop.order.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "creation_date", updatable = false)
    private LocalDate creationDate;

    @Column(name = "status_id")
    private Integer orderStatusId;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderPosition> orderPositions;
     */
}
