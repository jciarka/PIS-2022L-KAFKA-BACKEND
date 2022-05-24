package com.PIS2022L.kafkaconsumerapp.domain;


import com.PIS2022L.kafkaordermodels.domain.Address;
import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("dhl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoDhlOrder
{
    @Id
    private String id;
    private long purchasersCode;
    private Address pickupAddress;
    private Address deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;
    private LocalDateTime requiredDeliveryDate;
    private List<MongoDhlItem> items;
    private String remarks;
}


