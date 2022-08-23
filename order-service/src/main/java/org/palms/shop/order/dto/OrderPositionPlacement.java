package org.palms.shop.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO for order placement.
 */
@ApiModel(value = "OrderPositionPlacement", description = "Dto for adding product to order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderPositionPlacement {

    @ApiModelProperty(value = "Product id")
    private Long productId;
}
