package com.PIS2022L.kafkaconsumerapp.listeners;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.mappings.Converter;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSelgrosListener
{
    @Autowired
    private Converter<MongoSelgrosOrder, KafkaSelgrosOrder> mapper;

    @Autowired
    private SelgrosRepository repo;

    @KafkaListener(
            topics = KafkaTopic.SELGROS,
            groupId = "selgros-order-executors",
            containerFactory = "kafkaSelgrosListenerContainerFactory"
    )
    public void runSelgrosListener(final KafkaSelgrosOrder data)
    {
        System.out.println(data);
        final MongoSelgrosOrder order = mapper.convert(data);
        repo.insert(order);
    }
}
