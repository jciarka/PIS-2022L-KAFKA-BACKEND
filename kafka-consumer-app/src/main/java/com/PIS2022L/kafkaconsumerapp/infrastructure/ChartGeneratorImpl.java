package com.PIS2022L.kafkaconsumerapp.infrastructure;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class ChartGeneratorImpl implements ChartGenerator {

    @Override
    public byte[] GenerateBarChartAsPng(String title, String xlabel, String ylabel, Map<String, Long> data, int width, int height) throws IOException {
        JFreeChart barChart = ChartFactory.createBarChart(
            title,
            xlabel,
            ylabel,
            prepareBarChartDataset(xlabel, data),
            PlotOrientation.VERTICAL,
            true, true, true);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( width , height ) );

        ByteArrayOutputStream pngStream = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(
            pngStream,
            barChart,
            width,
            height
        );

        byte[] png = pngStream.toByteArray();
        pngStream.close();
        return png;
    }

    @Override
    public String GenerateBarChartAsBase64(String title, String xlabel, String ylabel, Map<String, Long> data, int width, int height) throws IOException {
        byte[] png = GenerateBarChartAsPng(title, xlabel, ylabel, data, width, height);
        return Base64.getEncoder().encodeToString(png);
    }

    private CategoryDataset prepareBarChartDataset(String xlabel, Map<String, Long> data) {
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        data.keySet().forEach(key -> dataset.addValue(data.get(key), key, xlabel));
        return dataset;
    }
}
