package com.PIS2022L.kafkaconsumerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class KafkaConsumerAppApplication
{

    public static void main(final String[] args)
    {
        SpringApplication.run(KafkaConsumerAppApplication.class, args);
    }
}
