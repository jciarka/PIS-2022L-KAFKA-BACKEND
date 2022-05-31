package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.services.OrderAggregationService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelReportImpl implements ExcelReport
{
    private final OrderAggregationService orderAggregationService;

    @Autowired
    public ExcelReportImpl(final OrderAggregationService orderAggregationService)
    {
        this.orderAggregationService = orderAggregationService;
    }


    @Override
    public byte[] getExcelReport(final String filename, final LocalDateTime dateFrom, final LocalDateTime dateTo) throws IOException
    {
        final Workbook workbook = openWorkbookFromTemplate();
        populateRows(workbook, dateFrom, dateTo);
        return createResponse(workbook);
    }

    private void populateRows(final Workbook workbook, final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        final List<MongoSelgrosOrder> selgrosItemList =
                orderAggregationService.getSelgrosOrdersCreatedInTimePeriod(dateFrom, dateTo);
        final Sheet sheet = workbook.getSheetAt(0);
        final Iterator<Row> rowIterator = sheet.rowIterator();

        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        for (final MongoSelgrosOrder order : selgrosItemList)
        {
            for (final MongoSelgrosItem orderItem : order.getItems())
            {
                final Row row = rowIterator.next();
                try
                {
                    row.getCell(1).setCellValue(order.getPurchasersCode());
                    row.getCell(2).setCellValue(Long.parseLong(orderItem.getEan()));
                    row.getCell(3).setCellValue(orderItem.getQuantity());
                    row.getCell(4).setCellValue(order.getReceivedAt().toLocalDate().toString());
                    row.getCell(5).setCellValue(order.getDeliveryAddress().getStreet());
                    row.getCell(6).setCellValue(order.getDeliveryAddress().getCity());
                    row.getCell(7).setCellValue(order.getDeliveryAddress().getPostalCode());
                    row.getCell(8).setCellValue(order.getDeliveryAddress().getCountryCode());
                    row.getCell(9).setCellValue(order.getContactPhone());
                    row.getCell(10).setCellValue(order.getRemarks());
                } catch (final NullPointerException ignored) { }
            }
        }
    }

    private Workbook openWorkbookFromTemplate() throws IOException
    {
        final File f = new ClassPathResource("templates/selgros_order_list_template.xlsx").getFile();
        final FileInputStream file = new FileInputStream(f);
        return new XSSFWorkbook(file);
    }

    private byte[] createResponse(final Workbook workbook)
    {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            workbook.write(os);
        } catch (final IOException ignored) { }

        return os.toByteArray();
    }
}
