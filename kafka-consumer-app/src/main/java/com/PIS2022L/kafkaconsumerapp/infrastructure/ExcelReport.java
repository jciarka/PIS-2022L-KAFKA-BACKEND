package com.PIS2022L.kafkaconsumerapp.infrastructure;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public interface ExcelReport
{
    byte[] getExcelReport(String filename, LocalDateTime dateFrom, LocalDateTime dateTo) throws IOException;
}
