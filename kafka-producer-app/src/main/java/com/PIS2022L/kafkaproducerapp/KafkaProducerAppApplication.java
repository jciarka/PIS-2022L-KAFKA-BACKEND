package com.PIS2022L.kafkaproducerapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import java.util.Arrays;

@SpringBootApplication
public class KafkaProducerAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerAppApplication.class, args);
	}
	//	@Bean
	//	CommandLineRunner commandLineKafkaSelgrosRunner(KafkaTemplate<String, KafkaSelgrosOrder> kafkaTemplate) {
	//		return args -> {
	//			KafkaSelgrosOrder order = new KafkaSelgrosOrder(3, Arrays.asList(
	//					new KafkaSelgrosItem("1", 5),
	//					new KafkaSelgrosItem("2", 5),
	//					new KafkaSelgrosItem("3", 5),
	//					new KafkaSelgrosItem("4", 5))
	//			);
	//			System.out.println("Sending");
	//			kafkaTemplate.send("selgros", order);
	//		};
	//	}

	// 	@Bean
	//	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
	//		return args -> {
	//			for (int i = 0; i < 1000; i++) {
	//				kafkaTemplate.send("pis", "hello world: " + i);
	//			}
	//		};
	//	}
	//
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
