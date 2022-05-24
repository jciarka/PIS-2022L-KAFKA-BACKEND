package com.PIS2022L.kafkaordermodels.domain.dhl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaDhlTransportItem {
    private Double weight;
    private Double width;
    private Double length;
    private Double height;
}
