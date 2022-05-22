package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaordermodels.domain.Address;

import java.util.Comparator;
import java.util.List;
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
        return  String.join(", ",
            items.stream()
                .sorted((MongoSelgrosOrder order1, MongoSelgrosOrder order2) -> {
                            int order1Len = order1.getItems() != null ?
                                order1.getItems().size() : 0;
                            int order2Len = order2.getItems() != null ?
                                    order2.getItems().size() : 0;

                            return  order1Len > order2Len ? 1 :
                                    order1Len == order2Len ? 0 : -1;
                    }
                )
                .limit(10)
                .map(x -> x.getPurchasersCode() + "(" + (x.getItems() != null ? x.getItems().size() : 0) + ")")
                .collect(Collectors.toList())
            );
    }
}
