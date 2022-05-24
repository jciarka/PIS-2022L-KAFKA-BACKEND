package com.PIS2022L.kafkaconsumerapp.models.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DhlItemsFilter {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long purchasersCode;
    private Double weightFrom;
    private Double weightTo;
    private Double widthFrom;
    private Double widthTo;
    private Double lengthFrom;
    private Double lengthTo;
    private Double heightFrom;
    private Double heightTo;
}
