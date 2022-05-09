package com.PIS2022L.kafkaconsumerapp.listeners;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KafkaConsumerAppApplicationTests {
	@Test
	@DisplayName("Default test 1")
	public void shouldReturnTrue() {
		assertEquals(42, Integer.sum(19, 23));
	}

	@Test
	@DisplayName("Default test 2")
	public void shouldAlsoReturnTrue() {
		assertEquals(42, Integer.sum(20, 22));
	}
}
