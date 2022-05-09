package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.mappings.SelgrosOrderConverter;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@Profile("test")
class OrderAggregationServiceTest
{
    @Autowired
    OrderAggregationService orderAggregationService;

    @MockBean
    SelgrosRepository selgrosRepository;

    @ParameterizedTest
    @ValueSource(strings = {"0:0", "1:1", "2:2"})
    public void shouldOrderAggregationServiceFilterEans(String eanAndCount)
    {
        String ean = eanAndCount.split(":")[0];
        int count = Integer.valueOf(eanAndCount.split(":")[1]);

        final MongoSelgrosOrder order1 = MongoSelgrosOrder.builder()
                .createdAt(LocalDateTime.parse("1900-04-01T00:00:00"))
                .items(
                        List.of(
                                new MongoSelgrosItem("1", 999),
                                new MongoSelgrosItem("2", 999)
                        )
                ).build();

        final MongoSelgrosOrder order2 = MongoSelgrosOrder.builder()
                .createdAt(LocalDateTime.parse("2022-04-04T00:00:00"))
                .items(
                        List.of(
                                new MongoSelgrosItem("2", 10)
                        )
                ).build();

        List<MongoSelgrosOrder> orders = Arrays.asList(order1, order2);

        when(selgrosRepository
            .findByFilters(
                null,
                null,
                null,
                ean
            ))
            .thenReturn(orders);

        final List<AggregatedItemDTO> itemsForTimePeriod = orderAggregationService.getSelgrosItems(null, null, null, ean);
        Assertions.assertTrue(itemsForTimePeriod.stream().map(x -> x.getEan().equals(ean)).count() == count);
    }
}