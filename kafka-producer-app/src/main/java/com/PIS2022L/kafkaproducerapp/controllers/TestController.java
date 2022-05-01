package com.PIS2022L.kafkaproducerapp.controllers;

import com.PIS2022L.kafkaordermodels.domain.Address;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/test/")
public class TestController
{
    @Autowired
    private KafkaTemplate<String, KafkaSelgrosOrder> kafkaTemplate;

    @GetMapping("SelgrosOrderExample")
    public KafkaSelgrosOrder getSelgrosOrderExample()
    {
        return new KafkaSelgrosOrder(
                4,
                new Address("PL", "Warszawa", "00-661", "Plac Politechniki", "1", null),
                48500500500L,
                LocalDateTime.now(),
                Arrays.asList(
                        new KafkaSelgrosItem("1", 5),
                        new KafkaSelgrosItem("2", 5),
                        new KafkaSelgrosItem("3", 5),
                        new KafkaSelgrosItem("4", 5)
                ),
                null
        );
    }
}
