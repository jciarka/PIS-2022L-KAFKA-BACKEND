package com.PIS2022L.kafkaconsumerapp;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.repositories.SelgrosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class KafkaConsumerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerAppApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(SelgrosRepository repo) {
//        return args -> {
//            List<MongoSelgrosOrder> list = Arrays.asList(
//                    new MongoSelgrosOrder(null, 3, Arrays.asList(
//                            new MongoSelgrosItem("1", 5),
//                            new MongoSelgrosItem("1", 5),
//                            new MongoSelgrosItem("1", 5),
//                            new MongoSelgrosItem("1", 5))
//                    )
//                );
//            repo.insert(list);
//
//            List<MongoSelgrosOrder> orders = repo.findByPurchasersCode(3);
//            orders.forEach(x -> System.out.println(x.getId() + " " + x.getPurchasersCode()));
//        };
//    }
}
