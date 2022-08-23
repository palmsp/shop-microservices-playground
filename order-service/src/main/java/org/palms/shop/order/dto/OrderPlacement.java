package org.palms.shop.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * DTO for order placement.
 */
@ApiModel(value = "OrderPlacement", description = "Dto for order creation and placement")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderPlacement {

    @ApiModelProperty("Customer id")
    private Long customerId;

    @ApiModelProperty(value = "List of products to order", required = true)
    private List<OrderPositionPlacement> orderPositions;

}
