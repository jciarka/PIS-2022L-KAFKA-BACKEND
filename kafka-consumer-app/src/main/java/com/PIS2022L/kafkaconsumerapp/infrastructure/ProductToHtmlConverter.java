package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;

import java.util.List;

public interface ProductToHtmlConverter {
    String convert(ProductAggregatedModel product, int index);

    String convert(List<ProductAggregatedModel> purchasers);
}
