package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.dto.DhlAggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.filters.DhlItemsFilter;
import com.PIS2022L.kafkaconsumerapp.repositories.DhlRepository;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderAggregationServiceImpl implements OrderAggregationService
{
    private final SelgrosRepository selgrosRepository;
    private final DhlRepository dhlRepository;

    @Autowired
    public OrderAggregationServiceImpl(final SelgrosRepository selgrosRepository, DhlRepository dhlRepository)
    {
        this.selgrosRepository = selgrosRepository;
        this.dhlRepository = dhlRepository;
    }

    @Override
    public List<MongoSelgrosItem> getSelgrosItemsCreatedInTimePeriod(final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        return selgrosRepository.findByReceivedAtBetween(dateFrom, dateTo).stream()
                .map(MongoSelgrosOrder::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<AggregatedItemDTO> getSelgrosItems(final LocalDateTime dateFrom, final LocalDateTime dateTo, final Long purchasersCode, final String ean)
    {
        Stream<AggregatedItemDTO> itemsStream = selgrosRepository
                .findByFilters(dateFrom, dateTo, purchasersCode, ean)
                .stream()
                .map(x -> x.getItems().stream().map(
                        y -> new AggregatedItemDTO(
                            y.getEan(),
                            y.getQuantity(),
                            x.getReceivedAt(),
                            x.getPurchasersCode())
                        ).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .filter(x -> ean == null || x.getEan().contains(ean));;

        return itemsStream.collect(Collectors.toList());
    }

    @Override
    public List<DhlAggregatedItemDTO> getDhlItems(DhlItemsFilter filter) {
        List<DhlAggregatedItemDTO> items = dhlRepository.findByFilters(
                filter.getDateFrom(),
                filter.getDateTo(),
                filter.getPurchasersCode(),
                filter.getWeightFrom(),
                filter.getWeightTo(),
                filter.getWidthFrom(),
                filter.getWidthTo(),
                filter.getLengthFrom(),
                filter.getLengthTo(),
                filter.getHeightFrom(),
                filter.getHeightTo()
        );
        return items;
    }
}
