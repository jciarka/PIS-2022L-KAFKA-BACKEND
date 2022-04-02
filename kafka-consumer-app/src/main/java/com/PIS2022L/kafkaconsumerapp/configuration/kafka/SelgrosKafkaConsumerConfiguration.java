package com.PIS2022L.kafkaconsumerapp.configuration.kafka;

import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
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
public class SelgrosKafkaConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> testKafkaSelgrosConsumerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    @Bean
    public ConsumerFactory<String, KafkaSelgrosOrder> testKafkaSelgrosConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                testKafkaSelgrosConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaSelgrosOrder.class));
    }

    @Bean("KafkaSelgrosListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, KafkaSelgrosOrder> testKafkaSelgrosListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaSelgrosOrder>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(testKafkaSelgrosConsumerFactory());

        return factory;
    }
}
