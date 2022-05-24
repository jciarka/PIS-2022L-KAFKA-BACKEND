package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.dto.DhlAggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.filters.DhlItemsFilter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface OrderAggregationService
{
    List<MongoSelgrosItem> getSelgrosItemsCreatedInTimePeriod(LocalDateTime dateFrom, LocalDateTime dateTo);
    List<AggregatedItemDTO> getSelgrosItems(LocalDateTime dateFrom, LocalDateTime dateTo, Long purchasersCode, String ean);
    List<DhlAggregatedItemDTO> getDhlItems(DhlItemsFilter filter);

}
