package com.PIS2022L.kafkaproducerapp.services;

import com.PIS2022L.kafkaproducerapp.utils.AbstractJUnitTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceTest extends AbstractJUnitTemplate
{
    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp() { }

    @Test
    public void validSelgrosOrderHandled()
    {
        
    }

    @Test
    public void invalidSelgrosOrderHandled()
    {

    }
}
