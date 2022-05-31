package com.PIS2022L.kafkaordermodels.domain.dhl;

import com.PIS2022L.kafkaordermodels.domain.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KafkaDhlTransportOrder {
    private long purchasersCode;
    private Address pickupAddress;
    private Address deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime requiredDeliveryDate;
    private List<KafkaDhlTransportItem> items;
    private String remarks;
}

