package com.rav.bhaj.kafka.strings;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.*;

public class StringConsumer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(StringConsumer.class);
	private static final boolean KEEP_ON_RUNNING = true;
	private static final String STRING_TOPIC_NAME = "STRING_TOPIC";
	private Consumer<String, String> consumer;

	public void run() {

		Properties consumerProperties = new Properties();
		consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "string-consumers");
		consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		consumer = new KafkaConsumer<>(consumerProperties);
		consumer.subscribe(asList(STRING_TOPIC_NAME));
		log.info("{} Topic subscription completed", STRING_TOPIC_NAME);

		try {
			while (KEEP_ON_RUNNING) {
				ConsumerRecords<String, String> studentNames = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> studentRecord : studentNames) {
					log.info("Received record from topic => {} with values:\n", studentRecord.topic());
					log.info("key => {}\n", studentRecord.key());
					log.info("AORMessage received => {}\n", studentRecord.value());
				}
			}
		} catch (WakeupException e) {
			log.error("Stopping {} topic consumer", STRING_TOPIC_NAME);
		} finally {
			consumer.close();
		}
	}
}
