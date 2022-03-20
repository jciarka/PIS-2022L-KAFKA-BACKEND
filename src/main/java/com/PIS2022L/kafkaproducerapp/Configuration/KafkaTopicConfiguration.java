package com.PIS2022L.kafkaproducerapp.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

// If we want to create topic here, no by bash kafka scripts
//    @Bean
//    public NewTopic pisTopic() {
//        return TopicBuilder.name("pis")
//                .build();
//    }
}
