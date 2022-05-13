package com.PIS2022L.kafkaconsumerapp.infrastructure;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Map;

@Component
public interface PdfGenerator {
    byte[] convertToPdf(String template, Map<String, String> substitutions) throws IOException;

    byte[] convertToPdf(String template, Map<String, String> substitutions, String basePath) throws IOException ;
}
