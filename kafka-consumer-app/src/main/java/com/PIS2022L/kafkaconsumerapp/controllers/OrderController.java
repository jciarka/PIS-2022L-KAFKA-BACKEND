package com.PIS2022L.kafkaconsumerapp.controllers;


import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemsDTO;
import com.PIS2022L.kafkaconsumerapp.services.OrderAggregationService;
import com.PIS2022L.kafkaconsumerapp.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = PathConstant.ORDER_PATH)
public class OrderController
{
    private final OrderAggregationService orderAggregationService;
    private final TimeService timeService;

    @Autowired
    public OrderController(
            final OrderAggregationService orderAggregationService,
            final TimeService timeService)
    {
        this.orderAggregationService = orderAggregationService;
        this.timeService = timeService;
    }

    @GetMapping(PathConstant.SELGROS_ITEMS)
    public AggregatedItemsDTO getAggregatedItems(@RequestParam("dateFrom") final Long unixDateFrom, @RequestParam("dateTo") final Long unixDateTo)
    {
        // We should probably start using OffSetDateTime over LocaleDateTime
        final LocalDateTime dateFrom = timeService.convertUnixTimestampToLocaleDateTime(unixDateFrom);
        final LocalDateTime dateTo = timeService.convertUnixTimestampToLocaleDateTime(unixDateTo);
        final List<MongoSelgrosItem> selgrosItemsForTimePeriod =
                orderAggregationService.getSelgrosItemsCreatedInTimePeriod(dateFrom, dateTo);
        return new AggregatedItemsDTO(selgrosItemsForTimePeriod);
    }
}
