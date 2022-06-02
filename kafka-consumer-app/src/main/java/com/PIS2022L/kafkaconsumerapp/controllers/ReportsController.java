package com.PIS2022L.kafkaconsumerapp.controllers;

import com.PIS2022L.kafkaconsumerapp.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = PathConstant.REPORTS_PATH)
public class ReportsController
{
    private final ReportsService reportsService;

    @Autowired
    public ReportsController(final ReportsService reportsService)
    {
        this.reportsService = reportsService;
    }

    @GetMapping(
            value = "clients/byOrders",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    ResponseEntity<byte[]> GetTopPurchasersByOrdersCount(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateTo,
            @RequestParam(name = "limit", required = false, defaultValue = "10") final Integer limit
    )
    {
        try
        {
            return new ResponseEntity<>(reportsService.TopPurchasersByOrdersCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (final IOException e)
        {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "clients/byItems",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    ResponseEntity<byte[]> GetTopPurchasersByItemsCount(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateTo,
            @RequestParam(name = "limit", required = false, defaultValue = "10") final Integer limit
    )
    {
        try
        {
            return new ResponseEntity<>(reportsService.TopPurchasersByItemsCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (final IOException e)
        {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "products",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    ResponseEntity<byte[]> GetTopProducts(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateTo,
            @RequestParam(name = "limit", required = false, defaultValue = "10") final Integer limit
    )
    {
        try
        {
            return new ResponseEntity<>(reportsService.TopProduct(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (final IOException e)
        {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("excel")
    public @ResponseBody
    ResponseEntity<byte[]> getExcelReport(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo)
    {
        try
        {
            dateFrom = Objects.isNull(dateFrom) ? LocalDateTime.parse("2001-01-01T10:15:30.") : dateFrom;
            dateTo = Objects.isNull(dateTo) ? LocalDateTime.now() : dateTo;
            final byte[] report = reportsService.excelReportForDates(dateFrom, dateTo);

            final HttpHeaders headers = addWorkbookResponseHeaders(report, dateFrom, dateTo);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(report.length)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(report);

        } catch (final IOException e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpHeaders addWorkbookResponseHeaders(final byte[] bytes, final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        final String filename = new StringBuilder("SELGROS_ORDERS")
                .append("  ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(".xlsx")
                .toString();

        headers.add("Content-Disposition", "attachment; filename=" + filename);
        return headers;
    }

}
