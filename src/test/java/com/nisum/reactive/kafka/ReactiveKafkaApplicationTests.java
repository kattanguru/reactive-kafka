package com.nisum.reactive.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(topics = {"${CUSTOMER_TOPIC}"})
class ReactiveKafkaApplicationTests {

	@Test
	void main_contextLoads_DoesNotThrow() {
		assertDoesNotThrow(() -> ReactiveKafkaApplication.main(new String[]{"--server.port=0"}));
	}

}