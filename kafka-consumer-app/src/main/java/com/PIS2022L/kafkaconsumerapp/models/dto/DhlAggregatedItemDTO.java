package com.PIS2022L.kafkaconsumerapp.models.dto;

import com.PIS2022L.kafkaconsumerapp.domain.MongoDhlItem;
import com.PIS2022L.kafkaordermodels.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DhlAggregatedItemDTO {
    @Id
    private String id;
    private long purchasersCode;
    private Address pickupAddress;
    private Address deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;
    private LocalDateTime requiredDeliveryDate;
    private String remarks;
    private Double weight;
    private Double width;
    private Double length;
    private Double height;
}
