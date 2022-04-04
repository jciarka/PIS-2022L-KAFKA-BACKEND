package com.PIS2022L.kafkaordermodels.domain.selgros;


import com.PIS2022L.kafkaordermodels.domain.Adress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaSelgrosOrder {
    private long purchasersCode;
    private Adress deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private List<KafkaSelgrosItem> items;
    private String remarks;
}
