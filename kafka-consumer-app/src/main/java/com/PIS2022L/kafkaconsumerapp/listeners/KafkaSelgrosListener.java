package com.PIS2022L.kafkaconsumerapp.listeners;

import com.PIS2022L.kafkaconsumerapp.configuration.mappings.Converter;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSelgrosListener {

    @Autowired
    private Converter<MongoSelgrosOrder, KafkaSelgrosOrder> mapper;

    @Autowired
    private SelgrosRepository repo;

    @KafkaListener(
            topics = "selgros",
            groupId = "selgros-order-executors",
            containerFactory = "KafkaSelgrosListenerContainerFactory"
    )
    void runSelgrosListener(KafkaSelgrosOrder data) {
        MongoSelgrosOrder order = mapper.convert(data);
        repo.insert(order);
    }
}
