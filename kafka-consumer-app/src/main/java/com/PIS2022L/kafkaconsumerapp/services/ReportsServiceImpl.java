package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.infrastructure.ChartGenerator;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PdfGenerator;
import com.PIS2022L.kafkaconsumerapp.infrastructure.ProductToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PurchaserToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ReportsServiceImpl implements ReportsService {

    private final SelgrosRepository selgrosRepository;

    private final PdfGenerator pdfGenerator;

    private final ChartGenerator chartGenerator;

    @Autowired
    public ReportsServiceImpl(final SelgrosRepository selgrosRepository, final PdfGenerator pdfGenerator, final ChartGenerator chartGenerator)
    {
        this.selgrosRepository = selgrosRepository;
        this.pdfGenerator = pdfGenerator;
        this.chartGenerator = chartGenerator;
    }

    @Override
    public byte[] TopPurchasersByOrdersCount(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException {
        List<PurchaserAggregatedModel> clients = selgrosRepository.findTopPurchasersByOrdersCount(dateFrom, dateTo, limit);

        Map<String, String> pdfData = preparePdfDataMapByOrdersCount(limit, clients);
        String template = prepareTemplateByOrdersCount();

        byte[] pdf = pdfGenerator.convertToPdf(template, pdfData,"classpath:/templates/");

        return pdf;
    }

    private String prepareTemplateByOrdersCount() {
        InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNPurchasersByOrderCount.html");

        String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> preparePdfDataMapByOrdersCount(int limit, List<PurchaserAggregatedModel> purchasers) throws IOException {
        Map<String, Long> purchasersTotals = new HashMap<>();
        purchasers.forEach(x -> purchasersTotals.put(String.valueOf(x.getPurchasersCode()), x.getTotal()));

        Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by order count");
        pdfData.put("_TABLE_CONTENT_", PurchaserToHtmlConverter.convert(purchasers));
        pdfData.put(
            "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top purchasers", "purchaser", "orders count", purchasersTotals, 400, 300 )
        );
        return pdfData;
    }

    @Override
    public byte[] TopPurchasersByItemsCount(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException {
        List<PurchaserAggregatedModel> clients = selgrosRepository.findTopPurchasersByProductsCount(dateFrom, dateTo, limit);

        Map<String, String> pdfData = preparePdfDataMapByItemsCount(limit, clients);
        String template = prepareTemplateByItemsCount();

        byte[] pdf = pdfGenerator.convertToPdf(template, pdfData,"classpath:/templates/");

        return pdf;
    }

    private String prepareTemplateByItemsCount() {
        InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNPurchasersByItemsCount.html");

        String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> preparePdfDataMapByItemsCount(int limit, List<PurchaserAggregatedModel> purchasers) throws IOException {
        Map<String, Long> purchasersTotals = new HashMap<>();
        purchasers.forEach(x -> purchasersTotals.put(String.valueOf(x.getPurchasersCode()), x.getTotal()));

        Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by items count");
        pdfData.put("_TABLE_CONTENT_", PurchaserToHtmlConverter.convert(purchasers));
        pdfData.put(
                "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top purchasers", "purchaser", "items count", purchasersTotals, 400, 300 )
        );
        return pdfData;
    }

    @Override
    public byte[] TopProduct(LocalDateTime dateFrom, LocalDateTime dateTo, int limit) throws IOException {
        List<ProductAggregatedModel> products = selgrosRepository.findTopProducts(dateFrom, dateTo, limit);

        Map<String, String> pdfData = prepareTopProductPdfDataMap(limit, products);
        String template = prepareTopProductTemplate();

        byte[] pdf = pdfGenerator.convertToPdf(template, pdfData,"classpath:/templates/");

        return pdf;
    }

    private String prepareTopProductTemplate() {
        InputStream templateStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/TopNProducts.html");

        String template = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
        return template;
    }

    private Map<String, String> prepareTopProductPdfDataMap(int limit, List<ProductAggregatedModel> products) throws IOException {
        Map<String, Long> productTotals = new HashMap<>();
        products.forEach(x -> productTotals.put(String.valueOf(x.getEan()), x.getItemsTotal()));

        Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " products");
        pdfData.put("_TABLE_CONTENT_", ProductToHtmlConverter.convert(products));
        pdfData.put(
                "_DATA_CHART_", "data:image/png;base64," + chartGenerator.GenerateBarChartAsBase64("Top products", "EAN", "items count", productTotals, 400, 300 )
        );
        return pdfData;
    }




}
