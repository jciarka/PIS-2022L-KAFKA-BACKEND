package com.PIS2022L.kafkaproducerapp.models.dtos;

import com.PIS2022L.kafkaordermodels.domain.Address;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
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
public class OrderDTO
{
    private long purchasersCode;
    private Address deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private List<KafkaSelgrosItem> items;
    private String remarks;
}
