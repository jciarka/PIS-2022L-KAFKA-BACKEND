package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAggregationServiceImpl implements OrderAggregationService
{
    private final SelgrosRepository selgrosRepository;

    @Autowired
    public OrderAggregationServiceImpl(final SelgrosRepository selgrosRepository)
    {
        this.selgrosRepository = selgrosRepository;
    }

    @Override
    public List<MongoSelgrosItem> getSelgrosItemsCreatedInTimePeriod(final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        /*
            Currently for the sake of simplicity it assumes that every document has the same format
            Missing 'CreatedAt' field might result in an error :/
        */
        return selgrosRepository.findAll().stream()
                .filter(order -> order.getCreatedAt().isAfter(dateFrom))
                .filter(order -> order.getCreatedAt().isBefore(dateTo))
                .map(MongoSelgrosOrder::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
