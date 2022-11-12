package com.rav.bhaj.kafka.strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);

	public static void main(String[] args) throws InterruptedException {

		LOGGER.info("Starting Consumer");
		Thread studentNameConsumerThread = new Thread(new StringConsumer());
		studentNameConsumerThread.start();

		LOGGER.info("Calling produer to send student names");
		StringPrdocer studentNamesProducer = new StringPrdocer();
		studentNamesProducer.produce("Rama");
		Thread.sleep(100);
		studentNamesProducer.produce("Sita");
		Thread.sleep(100);
		studentNamesProducer.produce("Lakshmana");
		Thread.sleep(100);

	}

}
