package com.PIS2022L.kafkaproducerapp;

import com.PIS2022L.kafkaproducerapp.Models.KafkaModels.TestKafkaJson;
import com.PIS2022L.kafkaproducerapp.Models.KafkaModels.TestKafkaJsonDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaProducerAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerAppApplication.class, args);
	}
/*
 	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> {
			for (int i = 0; i < 1000; i++) {
				kafkaTemplate.send("pis", "hello world: " + i);
			}
		};
	}
*/

// 	@Bean
//	CommandLineRunner commandLineTestKafkaJsonRunner(KafkaTemplate<String, TestKafkaJson> kafkaTemplate) {
//		return args -> {
//			TestKafkaJson test = new TestKafkaJson();
//			test.setElem1("Hello Kafka");
//			test.setDetails(new TestKafkaJsonDetails());
//
//			for (int i = 0; i < 10; i++) {
//				test.getDetails().setData(Integer.valueOf(i).toString());
//				kafkaTemplate.send("pis-json", test);
//			}
//		};
//	}
}
