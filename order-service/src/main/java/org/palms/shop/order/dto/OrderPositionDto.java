package org.palms.shop.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.palms.shop.order.domain.OrderPosition;

@ApiModel(value = "OrderPositionDto", description = "DTO for order position information")
@Data
@Builder
public class OrderPositionDto {

    @ApiModelProperty("Order position id")
    private Long id;

    @ApiModelProperty("Order id")
    private Long orderId;

    @ApiModelProperty("Product id")
    private Long productId;

    @ApiModelProperty("Product name")
    private String productName;

    public static OrderPositionDto from(OrderPosition orderPosition) {
        return OrderPositionDto.builder()
                .id(orderPosition.getId())
                .orderId(orderPosition.getOrderId())
                .productName(orderPosition.getProductName())
                .productId(orderPosition.getProductExternalId())
                .build();
    }
}
