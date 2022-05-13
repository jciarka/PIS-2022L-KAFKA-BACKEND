package com.PIS2022L.kafkaconsumerapp.controllers;

import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemsDTO;
import com.PIS2022L.kafkaconsumerapp.services.PurchaserReportsService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = PathConstant.REPORTS_PATH)
public class ReportsController {
    private final PurchaserReportsService purchaserService;

    public ReportsController(PurchaserReportsService purchaserService) {
        this.purchaserService = purchaserService;
    }

    @GetMapping(
            value = "clients/byOrders",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody ResponseEntity<byte[]> GetTopPurchasersByOrdersCount(
        @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
        @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
        @RequestParam(name = "purchasersCode", required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return new ResponseEntity<>(purchaserService.TopPurchasersByOrdersCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
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
            @RequestParam(name = "purchasersCode", required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return new ResponseEntity<>(purchaserService.TopPurchasersByItemsCount(dateFrom, dateTo, limit.intValue()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping(
//            produces = MediaType.APPLICATION_PDF_VALUE
//    )
//    public @ResponseBody byte[] getEmptyDocument() throws IOException {
//        InputStream templateStream = this.getClass()
//            .getClassLoader()
//            .getResourceAsStream("templates/default.html");
//
//        String html = new BufferedReader(new InputStreamReader(templateStream)).lines().collect(Collectors.joining("\n"));
//
//        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
//
//        ConverterProperties properties = new ConverterProperties().setBaseUri("classpath:/templates/");
//        HtmlConverter.convertToPdf(html, pdf, properties);
//
//        byte[] content = pdf.toByteArray();
//        pdf.close();
//        return content;
//    }
//    public @ResponseBody byte[] getEmptyDocument() throws IOException, DocumentException {
//        File templateFile = new File(
//            this.getClass()
//                .getClassLoader()
//                .getResource("templates/default.html")
//                .getFile());
//
//        Document document = Jsoup.parse(templateFile, "UTF-8");
//        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
//        String xhtml = document.html();
//
//        ITextRenderer renderer = new ITextRenderer();
//        SharedContext sharedContext = renderer.getSharedContext();
//        sharedContext.setPrint(true);
//        sharedContext.setInteractive(false);
//        sharedContext.getTextRenderer().setSmoothingThreshold(0);
//
//        String baseUrl = "classpath:/templates/";
//        renderer.setDocumentFromString(xhtml, baseUrl);
//        renderer.layout();
//
//        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
//
//        renderer.createPDF(pdf);
//
//        byte[] content = pdf.toByteArray();
//        pdf.close();
//        return content;
//    }
}
