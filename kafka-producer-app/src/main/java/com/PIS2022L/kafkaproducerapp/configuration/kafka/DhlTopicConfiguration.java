package com.PIS2022L.kafkaproducerapp.configuration.kafka;

import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DhlTopicConfiguration
{
    @Bean(name = "DhlTopic")
    public NewTopic orderTopic()
    {
        return TopicBuilder.name(KafkaTopic.DHL)
                .build();
    }
}
