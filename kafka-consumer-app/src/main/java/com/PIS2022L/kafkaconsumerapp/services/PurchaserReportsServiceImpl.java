package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.infrastructure.PdfGenerator;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PurchaserToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PurchaserReportsServiceImpl implements PurchaserReportsService {

    private final SelgrosRepository selgrosRepository;

    private final PdfGenerator pdfGenerator;

    @Autowired
    public PurchaserReportsServiceImpl(final SelgrosRepository selgrosRepository, final PdfGenerator pdfGenerator)
    {
        this.selgrosRepository = selgrosRepository;
        this.pdfGenerator = pdfGenerator;
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

    private Map<String, String> preparePdfDataMapByOrdersCount(int limit, List<PurchaserAggregatedModel> clients) {
        Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_TABLE_CONTENT_", PurchaserToHtmlConverter.convert(clients));
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by order count");
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

    private Map<String, String> preparePdfDataMapByItemsCount(int limit, List<PurchaserAggregatedModel> clients) {
        Map<String, String> pdfData = new HashMap<>();
        pdfData.put("_TABLE_CONTENT_", PurchaserToHtmlConverter.convert(clients));
        pdfData.put("_DOCUMENT_NAME_", "Top " + limit + " purchasers by items count");
        return pdfData;
    }

}
