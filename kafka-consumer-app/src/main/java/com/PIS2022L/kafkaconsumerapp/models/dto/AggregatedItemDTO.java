package com.PIS2022L.kafkaconsumerapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedItemDTO {
    private String ean;
    private Integer quantity;
    private LocalDateTime recievedAt;
    private Long purchasersCode;
}
