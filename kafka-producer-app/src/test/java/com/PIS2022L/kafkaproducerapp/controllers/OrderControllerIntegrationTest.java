package com.PIS2022L.kafkaproducerapp.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    private static String mockRequestBody;

    @BeforeAll
    static void setUpBeforeAll()
    {
        mockRequestBody = "{ \"purchasersCode\": 6, \"deliveryAddress\": { \"countryCode\": \"PL\", \"city\": \"tyototototodd\", \"postalCode\": \"00-661\", \"street\": \"Plac DUPA\", \"buildingNumber\": \"1\", \"flatNumber\": null }, \"contactPhone\": 48500500500, \"createdAt\": [ 2022, 4, 20, 2, 36, 17, 438470000 ], \"items\": [ { \"quantity\": 1000, \"ean\": \"1\" }, { \"quantity\": 20, \"ean\": \"2\" }, { \"quantity\": 30, \"ean\": \"3\" }, { \"quantity\": 2, \"ean\": \"4\" } ], \"remarks\": null }";
    }

    @Test
    public void postSelgrosOrderValidRequest() throws Exception
    {
        final String responseContent = "{\"message\":\"A new selgros order has been retrieved!\"}";
        mockMvc.perform(post("/api/order/selgros").contentType(MediaType.APPLICATION_JSON).content(mockRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));
    }

    @Test
    public void postSelgrosOrderInvalidRequest() throws Exception
    {
        mockMvc.perform(post("/api/order/selgros"))
                .andExpect(status().isBadRequest());
    }

}
