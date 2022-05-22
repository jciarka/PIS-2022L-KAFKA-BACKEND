package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PurchaserToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaordermodels.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Profile("test")
class PurchaserToHtmlConverterTest
{
    @Autowired
    private PurchaserToHtmlConverter converter;

    private List<PurchaserAggregatedModel> testPurchaserAggregatedModel;

    @BeforeEach
    public void generateTestData() {
        PurchaserAggregatedModel model1 = PurchaserAggregatedModel.builder()
                .purchasersCode(1)
                .total(10)
                .contactPhone(123123132L)
                .deliveryAddress(new Address("Mazowieckie","Warszawa", "01-001", "Nowowiejska", "1", null ))
                .items(
                        List.of(
                                new MongoSelgrosItem("1", 2),
                                new MongoSelgrosItem("2", 1),
                                new MongoSelgrosItem("3", 2),
                                new MongoSelgrosItem("4", 1),
                                new MongoSelgrosItem("5", 2)
                        )
                )
                .build();

        PurchaserAggregatedModel model2 = PurchaserAggregatedModel.builder()
                .purchasersCode(2)
                .total(5)
                .contactPhone(321231321L)
                .deliveryAddress(new Address())
                .deliveryAddress(new Address("Podkarpackie","Kraków", "01-001", "street", "10", "1" ))
                .items(
                        List.of(
                                new MongoSelgrosItem("1", 1),
                                new MongoSelgrosItem("2", 2)
                        )
                )
                .build();

         testPurchaserAggregatedModel =  List.of(model1, model2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    public void shouldLineIndexChangeCorrectly(String indexStr) throws IOException {
        int index = Integer.parseInt(indexStr);

        String result = this.converter.convert(testPurchaserAggregatedModel.get(1), index);
        Assertions.assertEquals(result.substring(8, 8 + indexStr.length()), indexStr);
    }

    @Test
    public void shoudItemConverterResultBeWrappedWithTr() throws IOException {
        String result = this.converter.convert(testPurchaserAggregatedModel.get(1), 1);
        Assertions.assertEquals(result.substring(0,3), "<tr");
        Assertions.assertEquals(result.substring((result.length() - 5)), "</tr>");
    }

    @Test
    public void shoudItemConverterResultHas5Columns() throws IOException {
        String result = this.converter.convert(testPurchaserAggregatedModel.get(0), 1);

        int occurrencesBeginCount = 0;
        int index = result.indexOf("<td", 0);

        while (index != -1) {
            occurrencesBeginCount += 1;
            index = result.indexOf("<td", index + 1);
        }

        int occurrencesEndCount = 0;
        index = result.indexOf("</td>", 0);

        while (index != -1) {
            occurrencesEndCount += 1;
            index = result.indexOf("</td>", index + 1);
        }

        Assertions.assertEquals(occurrencesBeginCount, 5);
        Assertions.assertEquals(occurrencesEndCount, 5);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0:<tr><td>1</td><td>1</td><td>10</td><td>01-001 Warszawa\nNowowiejska 1\n</td><td>1(2), 3(2), 5(2), 2(1), 4(1)</td></tr>",
            "1:<tr><td>1</td><td>2</td><td>5</td><td>01-001 Kraków\nstreet 10/1\n</td><td>2(2), 1(1)</td></tr>"
    })
    public void shouldConverterReturnPredefinedValueForEachRow(String pattern) throws IOException {
        int index = Integer.parseInt(pattern.split(":")[0]);
        String phrase = pattern.split(":")[1];

        String result = this.converter.convert(testPurchaserAggregatedModel.get(index), 1);
        Assertions.assertEquals(result, phrase);
    }

    @Test
    public void shouldConverterReturnPredefinedValue() throws IOException {
        String result = this.converter.convert(testPurchaserAggregatedModel);
        Assertions.assertEquals(result, "<tr><tr><td>1</td><td>1</td><td>10</td><td>01-001 Warszawa\nNowowiejska 1\n</td><td>1(2), 3(2), 5(2), 2(1), 4(1)</td></tr><tr><td>2</td><td>2</td><td>5</td><td>01-001 Kraków\nstreet 10/1\n</td><td>2(2), 1(1)</td></tr></tr>");
    }
}