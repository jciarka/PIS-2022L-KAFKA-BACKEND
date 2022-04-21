package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Profile("test")
class OrderAggregationServiceTest
{
    @Autowired
    OrderAggregationService orderAggregationService;

    @MockBean
    SelgrosRepository selgrosRepository;

    @Test
    public void getSelgrosItemsForTimePeriod()
    {
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
                                new MongoSelgrosItem("3", 10),
                                new MongoSelgrosItem("4", 10)
                        )
                ).build();
        final List<MongoSelgrosOrder> mockSelgrosOrder = List.of(order1, order2);
        doReturn(mockSelgrosOrder)
                .when(selgrosRepository)
                .findAll();

        final LocalDateTime dateFrom = LocalDateTime.parse("2022-04-01T00:00:00");
        final LocalDateTime dateTo = LocalDateTime.parse("2022-04-20T00:00:00");
        final List<MongoSelgrosItem> itemsForTimePeriod = orderAggregationService.getSelgrosItemsCreatedInTimePeriod(dateFrom, dateTo);

        Assertions.assertNotNull(itemsForTimePeriod);
        Assertions.assertEquals(20, itemsForTimePeriod.stream().mapToInt(MongoSelgrosItem::getQuantity).sum());
    }
}