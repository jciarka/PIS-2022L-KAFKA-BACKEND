package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaordermodels.domain.Address;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductToHtmlConverter {
    public static String convert(ProductAggregatedModel product, int index) {
        StringBuilder builder = new StringBuilder();
        builder.append("<tr>");

        builder.append("<td>" + index + "</td>");
        builder.append("<td>" + product.getEan() +"</td>");
        builder.append("<td>" + product.getItemsTotal() +"</td>");
        builder.append("<td>" + product.getOrdersTotal() + "</td>");
        builder.append("<td>" + convertOrders(product.getOrders()) + "</td>");

        builder.append("</tr>");
        return builder.toString();
    }

    public static String convert(List<ProductAggregatedModel> purchasers) {
        StringBuilder builder = new StringBuilder();

        List<ProductAggregatedModel> sortedPurchasers = purchasers.stream()
                .sorted(Comparator.comparingLong(ProductAggregatedModel::getOrdersTotal).reversed())
                .collect(Collectors.toList());

        builder.append("<tr>");
        for (int i = 1; i <= sortedPurchasers.size(); i++) {
            builder.append(convert(sortedPurchasers.get(i-1), i));
        }
        builder.append("</tr>");

        return  builder.toString();
    }

    private static String convertOrders(List<MongoSelgrosOrder> items) {
            Map<Long, List<MongoSelgrosOrder>> ordersPerPurchasers = items.stream()
                    .collect(Collectors.groupingBy(MongoSelgrosOrder::getPurchasersCode));

            return String.join(", ",
                ordersPerPurchasers.keySet()
                    .stream()
                    .sorted((Long purchaser1, Long purchaser2) -> {
                            int order1Len = ordersPerPurchasers.get(purchaser1) != null ?
                                    ordersPerPurchasers.get(purchaser1).size() : 0;
                            int order2Len = ordersPerPurchasers.get(purchaser2) != null ?
                                    ordersPerPurchasers.get(purchaser2).size() : 0;

                            return  order1Len < order2Len ? 1 :
                                    order1Len == order2Len ? 0 : -1;
                        }
                    )
                    .limit(10)
                    .map(x -> x + "(" + (ordersPerPurchasers.get(x) != null ? ordersPerPurchasers.get(x).size() : 0) + ")")
                    .collect(Collectors.toList())
            );
    }
}
