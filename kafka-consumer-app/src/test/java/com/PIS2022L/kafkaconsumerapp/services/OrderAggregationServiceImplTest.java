package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Profile("test")
class OrderAggregationServiceImplTest
{
    @Autowired
    OrderAggregationService orderAggregationService;

    @MockBean
    SelgrosRepository selgrosRepository;


    @Test
    public void getSelgrosItemsForTimePeriod()
    {
        final List<MongoSelgrosItem> mockItems = List.of(
                new MongoSelgrosItem("ean1", 3),
                new MongoSelgrosItem("ean2", 1)
        );
        doReturn(mockItems)
                .when(selgrosRepository)
                .getAllItemsForTimePeriod(anyLong(), anyLong());

        final LocalDateTime dateFrom = LocalDateTime.parse("2022-04-01T00:00:00");
        final LocalDateTime dateTo = LocalDateTime.parse("2022-04-20T00:00:00");
        final List<MongoSelgrosItem> selgrosItems = orderAggregationService.getSelgrosItemsCreatedInTimePeriod(dateFrom, dateTo);

        Assertions.assertEquals(2, selgrosItems.size());
    }
}