package com.PIS2022L.kafkaordermodels.domain.selgros;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaSelgrosOrder {
    private long purchasersCode;
    private List<KafkaSelgrosItem> items;
}
