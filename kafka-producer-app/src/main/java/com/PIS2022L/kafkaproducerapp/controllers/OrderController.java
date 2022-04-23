package com.PIS2022L.kafkaproducerapp.controllers;

import com.PIS2022L.kafkaproducerapp.controllers.responses.OrderResponse;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import com.PIS2022L.kafkaproducerapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = PathConstant.ORDER_PATH)
public class OrderController
{
    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping(PathConstant.SELGROS_RESOURCE)
    public ResponseEntity<OrderResponse> postSelgrosOrder(@RequestBody final OrderDTO orderDTO)
    {
        orderService.handleSelgrosOrder(orderDTO);
        return new ResponseEntity<>(new OrderResponse("A new selgros order has been retrieved!"), HttpStatus.OK);
    }
}
