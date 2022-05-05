package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.mongodb.client.model.ReturnDocument;
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

    @Autowired
    public OrderAggregationServiceImpl(final SelgrosRepository selgrosRepository)
    {
        this.selgrosRepository = selgrosRepository;
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
    public List<MongoSelgrosItem> getSelgrosItems(final LocalDateTime dateFrom, final LocalDateTime dateTo, final Long purchasersCode, final String ean)
    {
        Stream<MongoSelgrosItem> itemsStream = selgrosRepository
                .findByFiltes(dateFrom, dateTo, purchasersCode, ean)
                .stream()
                .map(MongoSelgrosOrder::getItems)
                .flatMap(Collection::stream)
                .filter(x -> ean == null || x.getEan().contains(ean));;

        return itemsStream.collect(Collectors.toList());
    }
}
