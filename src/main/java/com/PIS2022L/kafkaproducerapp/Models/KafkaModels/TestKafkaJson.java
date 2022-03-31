package com.PIS2022L.kafkaproducerapp.Models.KafkaModels;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestKafkaJson {
    private String elem1;
    private TestKafkaJsonDetails details;
}