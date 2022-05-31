package com.PIS2022L.kafkaconsumerapp.services;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public interface ReportsService
{
    public byte[] TopPurchasersByOrdersCount(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException;

    public byte[] TopPurchasersByItemsCount(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException;

    public byte[] TopProduct(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException;

    public byte[] excelReportForDates(LocalDateTime dateFrom, LocalDateTime dateTo) throws IOException;
}
