package com.PIS2022L.kafkaproducerapp.controllers;

import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {KafkaTopic.SELGROS}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerIntegrationTest
{
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private MockMvc mockMvc;

    private KafkaMessageListenerContainer<String, KafkaSelgrosOrder> container;
    private BlockingQueue<ConsumerRecord<String, KafkaSelgrosOrder>> consumerRecords;

    @BeforeEach
    void beforeAll()
    {
        consumerRecords = new LinkedBlockingQueue<>();
        final ContainerProperties containerProperties = new ContainerProperties(KafkaTopic.SELGROS);
        final Map<String, Object> consumerProperties =
                KafkaTestUtils.consumerProps("test-group", "false", embeddedKafkaBroker);

        final DefaultKafkaConsumerFactory<String, KafkaSelgrosOrder> kafkaCF = new DefaultKafkaConsumerFactory<>(
                consumerProperties, new StringDeserializer(), new JsonDeserializer<>(KafkaSelgrosOrder.class)
        );
        container = new KafkaMessageListenerContainer<>(kafkaCF, containerProperties);
        container.setupMessageListener((MessageListener<String, KafkaSelgrosOrder>) consumerRecords::add);

        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterEach
    public void tearDown()
    {
        container.stop();
    }

    @Test
    public void postSelgrosOrderValidRequest() throws Exception
    {
        final String mockRequestBody = "{\"purchasersCode\":6,\"deliveryAddress\":{\"countryCode\":\"PL\",\"city\":\"tyototototodd\",\"postalCode\":\"00-661\",\"street\":\"Plac DUPA\",\"buildingNumber\":\"1\",\"flatNumber\":null},\"contactPhone\":48500500500,\"createdAt\":[],\"items\":[{\"quantity\":1000,\"ean\":\"1\"},{\"quantity\":20,\"ean\":\"2\"},{\"quantity\":30,\"ean\":\"3\"},{\"quantity\":2,\"ean\":\"4\"}],\"remarks\":null}";
        final String responseContent = "{\"message\":\"A new selgros order has been retrieved!\"}";

        mockMvc.perform(post("/api/order/selgros").contentType(MediaType.APPLICATION_JSON).content(mockRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));

        final ConsumerRecord<String, KafkaSelgrosOrder> newOrder = consumerRecords.poll(10, TimeUnit.SECONDS);
        assertNotNull(newOrder);
        assertEquals(KafkaTopic.SELGROS, newOrder.topic());
    }

    @Test
    public void postSelgrosOrderInvalidRequest() throws Exception
    {
        mockMvc.perform(post("/api/order/selgros"))
                .andExpect(status().isBadRequest());
    }
}

