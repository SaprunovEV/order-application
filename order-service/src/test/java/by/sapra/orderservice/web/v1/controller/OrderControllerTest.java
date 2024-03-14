package by.sapra.orderservice.web.v1.controller;

import by.sapra.orderservice.service.OrderService;
import by.sapra.orderservice.web.controller.OrderController;
import by.sapra.orderservice.web.v1.model.OrderRequest;
import by.sapra.orderservice.web.v1.model.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrderService service;

    String uri = "/api/v1/order";

    @Test
    void whenCreateNewOrder_thenReturnCreated() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setProduct("test_product");
        request.setQuantity(3);

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void whenCreateNewOrder_thenCallService() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setProduct("test_product");
        request.setQuantity(3);

        OrderResponse response = new OrderResponse();
        when(service.createOrder(request))
                .thenReturn(response);

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());

        verify(service, times(1)).createOrder(request);
    }
}
