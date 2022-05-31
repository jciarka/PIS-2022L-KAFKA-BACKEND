package com.PIS2022L.kafkaconsumerapp.controllers;


import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemsDTO;
import com.PIS2022L.kafkaconsumerapp.models.dto.DhlAggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.models.dto.DhlAggregatedItemsDTO;
import com.PIS2022L.kafkaconsumerapp.models.filters.DhlItemsFilter;
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

    @GetMapping(PathConstant.SELGROS_ITEMS)
    public AggregatedItemsDTO getAggregatedItems(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(name = "purchasersCode", required = false) Long purchasersCode,
            @RequestParam(name = "ean", required = false) String ean
    )
    {
        final List<AggregatedItemDTO> selgrosItems =
                orderAggregationService.getSelgrosItems(dateFrom, dateTo, purchasersCode, ean);
        return new AggregatedItemsDTO(selgrosItems);
    }

    @GetMapping(PathConstant.DHL_ITEMS)
    public DhlAggregatedItemsDTO getAggregatedDhlItems(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(name = "purchasersCode", required = false) Long purchasersCode,
            @RequestParam(name = "weightFrom", required = false) Double weightFrom,
            @RequestParam(name = "weightTo", required = false) Double weightTo,
            @RequestParam(name = "widthFrom", required = false) Double widthFrom,
            @RequestParam(name = "widthTo", required = false) Double widthTo,
            @RequestParam(name = "lengthFrom", required = false) Double lengthFrom,
            @RequestParam(name = "lengthTo", required = false) Double lengthTo,
            @RequestParam(name = "heightFrom", required = false) Double heightFrom,
            @RequestParam(name = "heightTo", required = false) Double heightTo
    )
    {
        DhlItemsFilter filters = new DhlItemsFilter(dateFrom, dateTo, purchasersCode,
                weightFrom, weightTo, widthFrom, widthTo, lengthFrom, lengthTo, heightFrom, heightTo);

        final List<DhlAggregatedItemDTO> dhlItems = orderAggregationService.getDhlItems(filters);
        return new DhlAggregatedItemsDTO(dhlItems);
    }
}
