package com.rav.bhaj.kafka.strings;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringProducer {

	private static final Logger log = LoggerFactory.getLogger(StringProducer.class);
	private static final String STRING_TOPIC_NAME = "STRING_TOPIC";
	private Producer<String, String> producer;

	public void produce(String name) throws InterruptedException {
		// Producer Configurations
		Properties producerConfigurations = new Properties();
		producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		producerConfigurations.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());
		producerConfigurations.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class.getName());
		producerConfigurations.setProperty(ProducerConfig.ACKS_CONFIG, "all");
		producerConfigurations.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

		// Kafka Producer
		producer = new KafkaProducer<String, String>(producerConfigurations);

		// Kafka produer Record
		ProducerRecord<String, String> nameToInsert = new ProducerRecord<String, String>(STRING_TOPIC_NAME, name);

		// Kafka topic send
		producer.send(nameToInsert);
		log.info("Name {} sent to topic {}.", nameToInsert.value(), STRING_TOPIC_NAME);

	}
}
