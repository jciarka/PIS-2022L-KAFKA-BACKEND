package com.PIS2022L.kafkaproducerapp;

import com.PIS2022L.kafkaproducerapp.models.KafkaModels.TestKafkaJson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
//    @KafkaListener(
//            topics = "pis",
//            groupId = "pis-g1",
//            containerFactory = "StringConsumerContainerFactory"
//    )
//    void listener(String data) {
//        System.out.println("Received: " + data );
//    }

//    JSON listener
    @KafkaListener(
            topics = "pis-json",
            groupId = "pis-json-g1",
            containerFactory = "TestKafkaJsonListenerContainerFactory" // <- Can't find container factory without name
    )
    void testKafkaJsonListener(TestKafkaJson data) {
        System.out.println("Received: elem1:" + data.getElem1() + " elem2: " + data.getDetails().getData());
    }
}
