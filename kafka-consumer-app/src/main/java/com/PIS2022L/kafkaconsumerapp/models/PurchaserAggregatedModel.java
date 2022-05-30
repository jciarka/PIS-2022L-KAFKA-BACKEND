package com.PIS2022L.kafkaconsumerapp.models;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PurchaserAggregatedModel {
    private long purchasersCode;
    private long total;
    private Address deliveryAddress;
    private Long contactPhone;
    private List<MongoSelgrosItem> items;
}

