package com.PIS2022L.kafkaconsumerapp.configuration.kafka;

import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportOrder;
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
public class DhlKafkaConsumerConfiguration
{
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> kafkaConsumerConfig()
    {
        final HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    @Bean
    public ConsumerFactory<String, KafkaDhlTransportOrder> kafkaDhlConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                kafkaConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaDhlTransportOrder.class)
        );
    }

    @Bean(name = "kafkaDhlListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDhlTransportOrder> kafkaDhlListenerContainerFactory()
    {
        final ConcurrentKafkaListenerContainerFactory<String, KafkaDhlTransportOrder>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaDhlConsumerFactory());

        return factory;
    }
}
