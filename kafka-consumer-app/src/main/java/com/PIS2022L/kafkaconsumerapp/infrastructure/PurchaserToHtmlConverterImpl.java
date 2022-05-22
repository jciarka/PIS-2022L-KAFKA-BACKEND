package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaordermodels.domain.Address;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaserToHtmlConverterImpl implements PurchaserToHtmlConverter {
    @Override
    public String convert(PurchaserAggregatedModel purchaser, int index) {
        StringBuilder builder = new StringBuilder();
        builder.append("<tr>");

        builder.append("<td>" + index + "</td>");
        builder.append("<td>" + purchaser.getPurchasersCode() +"</td>");
        builder.append("<td>" + purchaser.getTotal() +"</td>");
        builder.append("<td>" + convertAddress(purchaser.getDeliveryAddress()) + "</td>");
        builder.append("<td>" + convertItems(purchaser.getItems()) + "</td>");

        builder.append("</tr>");
        return builder.toString();
    }

    @Override
    public String convert(List<PurchaserAggregatedModel> purchasers) {
        List<PurchaserAggregatedModel> sortedPurchasers = purchasers.stream()
                .sorted(Comparator.comparingLong(PurchaserAggregatedModel::getTotal).reversed())
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        builder.append("<tr>");

        for (int i = 1; i <= sortedPurchasers.size(); i++) {
            builder.append(convert(sortedPurchasers.get(i-1), i));
        }

        builder.append("</tr>");
        return  builder.toString();
    }

    private String convertItems(List<MongoSelgrosItem> items) {
        return  String.join(", ",
            items.stream()
                .sorted(Comparator.comparingInt(MongoSelgrosItem::getQuantity).reversed())
                .limit(10)
                .map(x -> x.getEan() + "(" + x.getQuantity() + ")")
                .collect(Collectors.toList())
            );
    }

    private String convertAddress(Address address) {
        StringBuilder builder = new StringBuilder();
        builder.append(address.getPostalCode() + " " + address.getCity() + "\n");
        builder.append(address.getStreet() + " " + address.getBuildingNumber() +
                (address.getFlatNumber() != null ? "/" + address.getFlatNumber() : "") + "\n");

        return  builder.toString();
    }
}
