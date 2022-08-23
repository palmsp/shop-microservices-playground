package org.palms.shop.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.palms.shop.order.controller.OrderController;
import org.palms.shop.order.dto.OrderDto;
import org.palms.shop.order.dto.OrderPlacement;
import org.palms.shop.order.dto.OrderPositionDto;
import org.palms.shop.order.dto.OrderPositionPlacement;
import org.palms.shop.order.dto.OrderStatus;
import org.palms.shop.order.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseContractTest {

    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(orderController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        mockGetOrder();
        mockPlaceOrder();
        mockGetOrdersForCustomer();
    }

    private void mockGetOrder() {
        when(orderService.getOrder(anyLong())).thenReturn(orderDto(1L));
    }

    private void mockPlaceOrder() {
        when(orderService.placeOrder(any(OrderPlacement.class))).thenReturn(orderDto(1L));
    }

    private void mockGetOrdersForCustomer() {
        OrderDto orderDto1 = orderDto(1L);
        OrderDto orderDto2 = orderDto(2L);
        when(orderService.getCustomerOrders(999000L)).thenReturn(List.of(orderDto1, orderDto2));
    }

    private OrderDto orderDto(Long orderId) {
        return OrderDto.builder()
                .id(orderId)
                .customerId(999000L)
                .orderStatus(OrderStatus.NEW)
                .orderPositions(orderPositionList(orderId))
                .build();
    }

    private List<OrderPositionDto> orderPositionList(Long orderId) {
        OrderPositionDto orderPositionDto1 = OrderPositionDto.builder()
                .id(2L)
                .orderId(orderId)
                .productId(111L).build();
        OrderPositionDto orderPositionDto2 = OrderPositionDto.builder()
                .id(3L)
                .orderId(orderId)
                .productId(222L).build();
        return List.of(orderPositionDto1, orderPositionDto2);
    }

    private OrderPlacement orderPlacement() {
        OrderPlacement orderPlacement = new OrderPlacement();
        orderPlacement.setCustomerId(999000L);
        OrderPositionPlacement orderPositionPlacement1 = new OrderPositionPlacement();
        orderPositionPlacement1.setProductId(111L);
        OrderPositionPlacement orderPositionPlacement2 = new OrderPositionPlacement();
        orderPositionPlacement2.setProductId(222L);
        orderPlacement.setOrderPositions(List.of(orderPositionPlacement1, orderPositionPlacement2));
        return orderPlacement;
    }
}
