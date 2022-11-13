package com.rav.bhaj.kafka.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.rav.bhaj.kafka.strings.TestRunner.class);

    public static void main(String[] args) throws InterruptedException {

        LOGGER.info("Starting Consumer");
        Thread studentNameConsumerThread = new Thread(new StudentConsumer());
        studentNameConsumerThread.start();

        LOGGER.info("Calling producer to send student objects");
        StudentProducer studentNamesProducer = new StudentProducer();
        studentNamesProducer.produce(123, "Rama","kafka");
        Thread.sleep(100);
        studentNamesProducer.produce(234, "Sita", "spark");
        Thread.sleep(100);
        studentNamesProducer.produce(345, "Lakshmana", "flink");
        Thread.sleep(100);

    }
}
