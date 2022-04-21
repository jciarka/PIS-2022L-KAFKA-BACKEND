package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface OrderAggregationService
{
    List<MongoSelgrosItem> getSelgrosItemsCreatedInTimePeriod(LocalDateTime dateFrom, LocalDateTime dateTo);
}
