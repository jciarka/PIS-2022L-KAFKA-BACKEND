package com.PIS2022L.kafkaconsumerapp.listeners;

import com.PIS2022L.kafkaconsumerapp.domain.MongoDhlOrder;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.mappings.Converter;
import com.PIS2022L.kafkaconsumerapp.repositories.DhlRepository;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportOrder;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaDhlListener
{
    @Autowired
    private Converter<MongoDhlOrder, KafkaDhlTransportOrder> mapper;

    @Autowired
    private DhlRepository repo;

    @KafkaListener(
            topics = KafkaTopic.DHL,
            groupId = "selgros-order-executors",
            containerFactory = "kafkaDhlListenerContainerFactory"
    )
    public void runSelgrosListener(final KafkaDhlTransportOrder data)
    {
        System.out.println(data);
        final MongoDhlOrder order = mapper.convert(data);
        repo.insert(order);
    }
}
