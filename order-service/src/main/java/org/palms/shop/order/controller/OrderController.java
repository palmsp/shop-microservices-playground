package org.palms.shop.order.controller;

import static org.palms.shop.order.controller.ApiConstants.ALL;
import static org.palms.shop.order.controller.ApiConstants.ID;
import static org.palms.shop.order.controller.ApiConstants.ORDER;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.palms.shop.order.dto.OrderDto;
import org.palms.shop.order.dto.OrderPlacement;
import org.palms.shop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("Operations with orders")
@RestController
@RequestMapping(ORDER)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ApiOperation(value = "Place order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderPlacement orderPlacement) {
        return ResponseEntity.ok(orderService.placeOrder(orderPlacement));
    }

    @GetMapping(ID)
    @ApiOperation(value = "Find order by id")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping(ALL)
    @ApiOperation(value = "Find all customer orders")
    public ResponseEntity<List<OrderDto>> getCustomer(@RequestParam("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getCustomerOrders(customerId));
    }
}
