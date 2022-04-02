package com.PIS2022L.kafkaconsumerapp.configuration.mappings;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;

public interface Converter<To, From> {
    To convert(From sourceOrder);
}
