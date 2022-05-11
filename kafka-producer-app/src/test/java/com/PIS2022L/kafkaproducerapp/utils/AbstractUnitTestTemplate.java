package com.PIS2022L.kafkaproducerapp.utils;

import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {KafkaTopic.SELGROS}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public abstract class AbstractUnitTestTemplate
{
}