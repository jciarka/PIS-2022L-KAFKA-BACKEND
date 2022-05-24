package com.PIS2022L.kafkaproducerapp.services;

import com.PIS2022L.kafkaproducerapp.models.dtos.DhlOrderDto;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public interface OrderService
{
    void handleSelgrosOrder(OrderDTO orderDTO);
    void handleDhlOrder(DhlOrderDto orderDTO);
}
