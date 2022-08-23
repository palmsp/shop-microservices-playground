package org.palms.shop.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.palms.shop.order.domain.Order;

@ApiModel(value = "OrderPositionDto", description = "DTO for order position information")
@Data
@Builder
public class OrderDto {

    @ApiModelProperty("Order id")
    private Long id;

    @ApiModelProperty("Customer id")
    private Long customerId;

    @ApiModelProperty("Creation date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @ApiModelProperty("Delivery  date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    @ApiModelProperty("Order status")
    private OrderStatus orderStatus;

    @ApiModelProperty("List of ordered products")
    private List<OrderPositionDto> orderPositions;

    public static OrderDto from(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .creationDate(order.getCreationDate())
                .orderStatus(OrderStatus.statusByid(order.getOrderStatusId()))
                .customerId(order.getCustomerId())
                .deliveryDate(order.getDeliveryDate())
                .build();
    }
}
