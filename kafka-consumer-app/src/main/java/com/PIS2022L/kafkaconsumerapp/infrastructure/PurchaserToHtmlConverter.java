package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;

import java.util.List;

public interface PurchaserToHtmlConverter {
    String convert(PurchaserAggregatedModel purchaser, int index);

    String convert(List<PurchaserAggregatedModel> purchasers);
}
