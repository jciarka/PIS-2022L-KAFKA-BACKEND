package com.PIS2022L.kafkaconsumerapp.models;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaordermodels.domain.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAggregatedModel {
    private String ean;
    private long itemsTotal;
    private long ordersTotal;
    private List<MongoSelgrosOrder> orders;
}

