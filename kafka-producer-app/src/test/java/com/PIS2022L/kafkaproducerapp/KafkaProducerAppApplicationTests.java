package com.PIS2022L.kafkaproducerapp;

import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {KafkaTopic.SELGROS}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaProducerAppApplicationTests
{
    @Test
    public void contextLoads(final ApplicationContext context)
    {
        assertNotNull(context);
    }
}
