package com.PIS2022L.kafkaconsumerapp.controllers;

import com.PIS2022L.kafkaconsumerapp.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = PathConstant.REPORTS_PATH)
public class ReportsController {
    private final ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping(
            value = "clients/byOrders",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody ResponseEntity<byte[]> GetTopPurchasersByOrdersCount(
        @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
        @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
        @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return new ResponseEntity<>(reportsService.TopPurchasersByOrdersCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "clients/byItems",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody ResponseEntity<byte[]> GetTopPurchasersByItemsCount(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return new ResponseEntity<>(reportsService.TopPurchasersByItemsCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "products",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody ResponseEntity<byte[]> GetTopProducts(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return new ResponseEntity<>(reportsService.TopProduct(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
