package com.PIS2022L.kafkaordermodels.domain.selgros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaSelgrosItem {
    private String Ean;
    private Integer Quantity;
}
