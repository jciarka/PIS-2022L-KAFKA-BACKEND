package com.PIS2022L.kafkaproducerapp.controllers;

import com.PIS2022L.kafkaordermodels.domain.Adress;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import com.PIS2022L.kafkaproducerapp.models.Test.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class TestController {
    @Autowired
    KafkaTemplate<String, KafkaSelgrosOrder> kafkaTemplate;

    @GetMapping()
    public ResponseEntity<TestResponse> TestResponse() {
        return new ResponseEntity<>(new TestResponse(), HttpStatus.OK);
    }

    @GetMapping("selgros")
    public ResponseEntity<TestResponse> AddTestSelgors() {
        KafkaSelgrosOrder order = new KafkaSelgrosOrder(4,
            new Adress("PL", "Warszawa", "00-661", "Plac Politechniki", "1", null),
            Long.valueOf(48500500500L),
            LocalDateTime.now(),
            Arrays.asList(
                new KafkaSelgrosItem("1", 5),
                new KafkaSelgrosItem("2", 5),
                new KafkaSelgrosItem("3", 5),
                new KafkaSelgrosItem("4", 5)),
            null
        );
        System.out.println("Sending");

        kafkaTemplate.send("selgros", order);

        return new ResponseEntity<>(new TestResponse(), HttpStatus.OK);
    }
}
