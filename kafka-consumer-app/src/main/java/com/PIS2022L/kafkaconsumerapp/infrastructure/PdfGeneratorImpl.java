package com.PIS2022L.kafkaconsumerapp.infrastructure;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Dictionary;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorImpl implements PdfGenerator {
    @Override
    public byte[] convertToPdf(String template, Map<String, String> substitutions) throws IOException  {
        return  convertToPdf(template, substitutions, null);
    }

    @Override
    public byte[] convertToPdf(final String template, Map<String, String> substitutions, String basePath) throws IOException {
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();

        String html = prepareHtml(template, substitutions);

        ConverterProperties properties = new ConverterProperties();

        if (basePath != null) {
            properties.setBaseUri(basePath);
        }

        HtmlConverter.convertToPdf(html, pdf, properties);
        byte[] content = pdf.toByteArray();
        pdf.close();

        return content;
    }

    private String prepareHtml(final String template, Map<String, String> substitutions) {
        String html = new String(template);
        for (String key : substitutions.keySet()) {
            html = html.replaceFirst(key, substitutions.get(key));
        }
        return html;
    }
}
