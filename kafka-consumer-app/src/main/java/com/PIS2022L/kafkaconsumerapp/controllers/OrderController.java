package com.PIS2022L.kafkaconsumerapp.controllers;


import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemsDTO;
import com.PIS2022L.kafkaconsumerapp.services.OrderAggregationService;
import com.PIS2022L.kafkaconsumerapp.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

//    @GetMapping(PathConstant.SELGROS_ITEMS)
//    public AggregatedItemsDTO getAggregatedItems(
//            @RequestParam(name = "dateFrom", required = false) Optional<Long> unixDateFrom,
//            @RequestParam(name = "dateTo", required = false) Optional<Long> unixDateTo)
//    {
//        // We should probably start using OffSetDateTime over LocaleDateTime
//        final LocalDateTime dateFrom = !unixDateFrom.isEmpty() ? timeService.convertUnixTimestampToLocaleDateTime(unixDateFrom.get()) : null;
//        final LocalDateTime dateTo = !unixDateTo.isEmpty() ? timeService.convertUnixTimestampToLocaleDateTime(unixDateTo.get()) : null;
//        final List<MongoSelgrosItem> selgrosItemsForTimePeriod =
//                orderAggregationService.getSelgrosItemsCreatedInTimePeriod(dateFrom, dateTo);
//        return new AggregatedItemsDTO(selgrosItemsForTimePeriod);
//    }

//    @GetMapping(PathConstant.SELGROS_ITEMS)
//    public AggregatedItemsDTO getAggregatedItems(
//            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
//            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo)
//    {
//        final List<MongoSelgrosItem> selgrosItemsForTimePeriod =
//                orderAggregationService.getSelgrosItemsCreatedInTimePeriod(dateFrom, dateTo);
//        return new AggregatedItemsDTO(selgrosItemsForTimePeriod);
//    }

    @GetMapping(PathConstant.SELGROS_ITEMS)
    public AggregatedItemsDTO getAggregatedItems(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(name = "purchasersCode", required = false) Long purchasersCode,
            @RequestParam(name = "ean", required = false) String ean
    )
    {
        final List<MongoSelgrosItem> selgrosItems =
                orderAggregationService.getSelgrosItems(dateFrom, dateTo, purchasersCode, ean);
        return new AggregatedItemsDTO(selgrosItems);
    }
}
