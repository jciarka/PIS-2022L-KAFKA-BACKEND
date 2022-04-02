package com.PIS2022L.kafkaproducerapp.configuration.kafkaConfig;

import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSelgrosProducerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, KafkaSelgrosOrder> KafkaSelgrosOrderProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, KafkaSelgrosOrder> KafkaSelgrosOrderTemplate(
            ProducerFactory<String, KafkaSelgrosOrder> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}