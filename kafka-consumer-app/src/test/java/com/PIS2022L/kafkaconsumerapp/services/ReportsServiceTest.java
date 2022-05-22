package com.PIS2022L.kafkaconsumerapp.services;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.infrastructure.ChartGenerator;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PdfGenerator;
import com.PIS2022L.kafkaconsumerapp.infrastructure.ProductToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.infrastructure.PurchaserToHtmlConverter;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.dto.AggregatedItemDTO;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import com.PIS2022L.kafkaordermodels.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static scala.reflect.ClassTag.Any;

@SpringBootTest
@Profile("test")
class ReportsServiceTest
{
    @Autowired
    ReportsService reportsService;

    @MockBean
    SelgrosRepository selgrosRepository;

    @MockBean
    PdfGenerator pdfGenerator;

    @MockBean
    ChartGenerator chartGenerator;

    @MockBean
    ProductToHtmlConverter productToHtmlConverter;

    @MockBean
    PurchaserToHtmlConverter purchaserToHtmlConverter;

    @BeforeEach
    public void generateTestData() {
        PurchaserAggregatedModel model1 = PurchaserAggregatedModel.builder()
                .purchasersCode(1)
                .total(10)
                .contactPhone(123123132L)
                .deliveryAddress(new Address())
                .items(
                        List.of(
                                new MongoSelgrosItem("1", 5),
                                new MongoSelgrosItem("2", 5)
                        )
                )
                .build();

        PurchaserAggregatedModel model2 = PurchaserAggregatedModel.builder()
                .purchasersCode(2)
                .total(5)
                .contactPhone(123123132L)
                .deliveryAddress(new Address())
                .items(
                        List.of(
                                new MongoSelgrosItem("1", 1),
                                new MongoSelgrosItem("2", 2)
                        )
                )
                .build();

         testPurchaserAggregatedModel =  List.of(model1, model2);
    }

    private List<PurchaserAggregatedModel> testPurchaserAggregatedModel;

    private void mockReportsServiceInjectedBeansMethods() throws IOException {
        when(selgrosRepository
                .findTopPurchasersByProductsCount(
                        null,
                        null,
                        10
                ))
                .thenReturn(testPurchaserAggregatedModel);

        when(purchaserToHtmlConverter.convert(any(List.class))).thenReturn("__BODY__");

        when(chartGenerator.GenerateBarChartAsBase64(
                anyString(),
                anyString(),
                anyString(),
                any(Map.class),
                anyInt(),
                anyInt())
        ).thenReturn("__BASE_64_IMAGE__");

        when(
                pdfGenerator.convertToPdf(
                        anyString(),
                        any(Map.class),
                        anyString()
                )).thenReturn(
                new byte[] {}
        );
    }

    @Test
    public void shouldPurchaserToHtmlConverterConvertBeCalledOnceAtTopPurchasersByItemsCount() throws IOException {
        this.mockReportsServiceInjectedBeansMethods();

        byte[] result = this.reportsService.TopPurchasersByItemsCount(
                null,
                null,
                10);

        verify(this.purchaserToHtmlConverter, times(1)).convert(any(List.class));
    }

    @Test
    public void shouldPurchaserToHtmlConverterConvertBeCalledWithCertainDataAtTopPurchasersByItemsCount() throws IOException {
        this.mockReportsServiceInjectedBeansMethods();

        byte[] result = this.reportsService.TopPurchasersByItemsCount(
                null,
                null,
                10);

        verify(this.purchaserToHtmlConverter).convert(this.testPurchaserAggregatedModel);
    }

    @Test
    public void shouldChartGeneratorGenerateBeCalledOnceAtTopPurchasersByItemsCount() throws IOException {
        this.mockReportsServiceInjectedBeansMethods();

        byte[] result = this.reportsService.TopPurchasersByItemsCount(
                null,
                null,
                10);

        verify(this.chartGenerator, times(1)).GenerateBarChartAsBase64(
                anyString(),
                anyString(),
                anyString(),
                any(Map.class),
                anyInt(),
                anyInt());
    }

    @Test
    public void shouldChartGeneratorGenerateBeCalledWithCertainDataAtTopPurchasersByItemsCount() throws IOException {
        this.mockReportsServiceInjectedBeansMethods();

        byte[] result = this.reportsService.TopPurchasersByItemsCount(
                null,
                null,
                10);

        Map<String, Long> purchasersTotals = new HashMap<>();
        testPurchaserAggregatedModel.forEach(x -> purchasersTotals.put(String.valueOf(x.getPurchasersCode()), x.getTotal()));
        verify(this.chartGenerator, times(1)).GenerateBarChartAsBase64(
                "Top purchasers", "purchaser", "items count", purchasersTotals, 400, 300);
    }

    @Test
    public void shouldPdfGeneratorGenerateBeCalledOnceAtTopPurchasersByItemsCount() throws IOException {
        this.mockReportsServiceInjectedBeansMethods();

        byte[] result = this.reportsService.TopPurchasersByItemsCount(
                null,
                null,
                10);

        verify(this.pdfGenerator, times(1)).convertToPdf(
                any(String.class),
                any(Map.class),
                any(String.class)
        );
    }
}