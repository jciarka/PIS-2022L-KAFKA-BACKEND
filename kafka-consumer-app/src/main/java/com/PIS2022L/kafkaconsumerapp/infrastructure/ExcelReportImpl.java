package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.services.OrderAggregationService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
        final Sheet sheet = workbook.getSheetAt(0);
        addDateHeader(sheet, dateFrom, dateTo);
        populateRows(sheet, dateFrom, dateTo);
        return createResponse(workbook);
    }

    private void addDateHeader(final Sheet sheet, final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        final Row firstRow = sheet.getRow(0);
        final String dateHeader = String.format("%s - %s", dateFrom.toLocalDate(), dateTo.toLocalDate());
        firstRow.getCell(5).setCellValue(dateHeader);
    }

    private void populateRows(final Sheet sheet, final LocalDateTime dateFrom, final LocalDateTime dateTo)
    {
        final List<MongoSelgrosOrder> selgrosItemList =
                orderAggregationService.getSelgrosOrdersCreatedInTimePeriod(dateFrom, dateTo);

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
                    row.getCell(2).setCellValue(orderItem.getEan());
                    row.getCell(3).setCellValue(orderItem.getQuantity());
                    row.getCell(4).setCellValue(order.getReceivedAt().toLocalDate().toString());
                    row.getCell(5).setCellValue(order.getDeliveryAddress().getStreet());
                    row.getCell(6).setCellValue(order.getDeliveryAddress().getCity());
                    row.getCell(7).setCellValue(order.getDeliveryAddress().getPostalCode());
                    row.getCell(8).setCellValue(order.getDeliveryAddress().getCountryCode());
                    row.getCell(9).setCellValue(formatContactPhone(order.getContactPhone()));
                    row.getCell(10).setCellValue(order.getRemarks());
                } catch (final NullPointerException ignored) { }
            }
        }
    }

    private String formatContactPhone(final Long contactPhone)
    {
        final String stringContactPhone = String.valueOf(contactPhone);

        if (stringContactPhone.length() == 9)
        {
            return stringContactPhone;
        }

        final String countryCallingCode = stringContactPhone.substring(0, 2);
        final String firstPart = stringContactPhone.substring(2, 5);
        final String secondPart = stringContactPhone.substring(5, 8);
        final String thirdPart = stringContactPhone.substring(8, 11);
        return String.format("(+%s) %s %s %s", countryCallingCode, firstPart, secondPart, thirdPart);
    }

    private Workbook openWorkbookFromTemplate() throws IOException
    {
        return WorkbookFactory.create(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("templates/selgros_order_list_template.xlsx"))
        );
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
