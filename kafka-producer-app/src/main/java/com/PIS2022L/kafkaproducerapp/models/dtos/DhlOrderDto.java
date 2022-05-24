package com.PIS2022L.kafkaproducerapp.models.dtos;

import com.PIS2022L.kafkaordermodels.domain.Address;
import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DhlOrderDto {
    private long purchasersCode;
    private Address pickupAddress;
    private Address deliveryAddress;
    private Long contactPhone;
    private LocalDateTime requiredDeliveryDate;
    private List<KafkaDhlTransportItem> items;
    private String remarks;
}
