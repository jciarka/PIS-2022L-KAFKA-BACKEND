package com.PIS2022L.kafkaproducerapp.configuration.test;

import com.PIS2022L.kafkaproducerapp.models.KafkaModels.TestKafkaJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaJsonConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> testKafkaJsonConsumerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    @Bean
    public ConsumerFactory<String, TestKafkaJson> testKafkaJsonConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                testKafkaJsonConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(TestKafkaJson.class));
    }

    @Bean("TestKafkaJsonListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, TestKafkaJson> testKafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TestKafkaJson>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(testKafkaJsonConsumerFactory());

        return factory;
    }
}
