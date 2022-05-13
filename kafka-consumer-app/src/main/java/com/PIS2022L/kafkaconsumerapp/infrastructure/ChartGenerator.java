package com.PIS2022L.kafkaconsumerapp.infrastructure;

import lombok.extern.apachecommons.CommonsLog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public interface ChartGenerator {
    byte[] GenerateBarChartAsPng(String title, String xLabel, String yLabel, Map<String, Long> data, int width, int height) throws IOException;
    String GenerateBarChartAsBase64(String title, String xLabel, String yLabel, Map<String, Long> data, int width, int height) throws IOException;
}
