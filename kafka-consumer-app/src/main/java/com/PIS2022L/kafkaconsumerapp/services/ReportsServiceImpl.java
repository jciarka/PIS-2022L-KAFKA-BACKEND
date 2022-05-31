package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.infrastructure.*;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ReportsServiceImpl implements ReportsService
{

    private final SelgrosRepository selgrosRepository;

    private final PdfGenerator pdfGenerator;

    private final ChartGenerator chartGenerator;

    private final ProductToHtmlConverter productToHtmlConverter;

    private final PurchaserToHtmlConverter purchaserToHtmlConverter;

    private final ExcelReport excelReport;

    @Autowired
    public ReportsServiceImpl(
            final SelgrosRepository selgrosRepository,
            final PdfGenerator pdfGenerator,
            final ChartGenerator chartGenerator,
            final ProductToHtmlConverter productToHtmlConverter,
            final PurchaserToHtmlConverter purchaserToHtmlConverter,
            final ExcelReport excelReport)
    {
        this.selgrosRepository = selgrosRepository;
        this.pdfGenerator = pdfGenerator;
        this.chartGenerator = chartGenerator;
        this.productToHtmlConverter = productToHtmlConverter;
        this.purchaserToHtmlConverter = purchaserToHtmlConverter;
        this.excelReport = excelReport;
    }

    @Override
    public byte[] TopPurchasersByOrdersCount(final LocalDateTime dateFrom, final LocalDateTime dateTo, final int limit) throws IOException
    {
        final List<PurchaserAggregatedModel> clients = selgrosRepository.findTopPurchasersByOrdersCount(dateFrom, dateTo, limit);

        final Map<String, String> pdfData = preparePdfDataMapByOrdersCount(limit, clients);
        final String template = prepareTemplateByOrdersCount();

        final byte[] pdf = pdfGenerator.convertToPdf(template, pdfData, "classpath:/templates/");

        return pdf;
    }

    private String prepareTemplateByOrdersCount()
    {
        final InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNPurchasersByOrderCount.html");

        final String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> preparePdfDataMapByOrdersCount(final int limit, final List<PurchaserAggregatedModel> purchasers) throws IOException
    {
        final Map<String, Long> purchasersTotals = new HashMap<>();
        purchasers.forEach(x -> purchasersTotals.put(String.valueOf(x.getPurchasersCode()), x.getTotal()));

        final Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by order count");
        pdfData.put("_TABLE_CONTENT_", purchaserToHtmlConverter.convert(purchasers));
        pdfData.put(
                "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top purchasers", "purchaser", "orders count", purchasersTotals, 400, 300)
        );
        return pdfData;
    }

    @Override
    public byte[] TopPurchasersByItemsCount(final LocalDateTime dateFrom, final LocalDateTime dateTo, final int limit) throws IOException
    {
        final List<PurchaserAggregatedModel> clients = selgrosRepository.findTopPurchasersByProductsCount(dateFrom, dateTo, limit);

        final Map<String, String> pdfData = preparePdfDataMapByItemsCount(limit, clients);
        final String template = prepareTemplateByItemsCount();

        final byte[] pdf = pdfGenerator.convertToPdf(template, pdfData, "classpath:/templates/");

        return pdf;
    }

    private String prepareTemplateByItemsCount()
    {
        final InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNPurchasersByItemsCount.html");

        final String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> preparePdfDataMapByItemsCount(final int limit, final List<PurchaserAggregatedModel> purchasers) throws IOException
    {
        final Map<String, Long> purchasersTotals = new HashMap<>();
        purchasers.forEach(x -> purchasersTotals.put(String.valueOf(x.getPurchasersCode()), x.getTotal()));

        final Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by items count");
        pdfData.put("_TABLE_CONTENT_", purchaserToHtmlConverter.convert(purchasers));
        pdfData.put(
                "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top purchasers", "purchaser", "items count", purchasersTotals, 400, 300)
        );
        return pdfData;
    }

    @Override
    public byte[] TopProduct(final LocalDateTime dateFrom, final LocalDateTime dateTo, final int limit) throws IOException
    {
        final List<ProductAggregatedModel> products = selgrosRepository.findTopProducts(dateFrom, dateTo, limit);

        final Map<String, String> pdfData = prepareTopProductPdfDataMap(limit, products);
        final String template = prepareTopProductTemplate();

        final byte[] pdf = pdfGenerator.convertToPdf(template, pdfData, "classpath:/templates/");

        return pdf;
    }

    private String prepareTopProductTemplate()
    {
        final InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNProducts.html");

        final String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> prepareTopProductPdfDataMap(final int limit, final List<ProductAggregatedModel> products) throws IOException
    {
        final Map<String, Long> productTotals = new HashMap<>();
        products.forEach(x -> productTotals.put(String.valueOf(x.getEan()), x.getItemsTotal()));

        final Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " products");
        pdfData.put("_TABLE_CONTENT_", productToHtmlConverter.convert(products));
        pdfData.put(
                "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top products", "EAN", "items count", productTotals, 400, 300)
        );
        return pdfData;
    }

    @Override
    public byte[] excelReportForDates(final LocalDateTime dateFrom, final LocalDateTime dateTo) throws IOException
    {
        return excelReport.getExcelReport("selgros_order_list_template.xlsx", dateFrom, dateTo);
    }

}
