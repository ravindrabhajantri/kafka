package com.rav.bhaj.kafka.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.rav.bhaj.kafka.objects.TestRunner.class);

    public static void main(String[] args) throws InterruptedException {

        LOGGER.info("Starting Consumer");
        Thread studentConsumerThread = new Thread(new StudentConsumer());
        studentConsumerThread.start();

        LOGGER.info("Calling producer to send student objects");
        StudentProducer studentProducer = new StudentProducer();
        studentProducer.produce(123, "Rama","kafka");
        Thread.sleep(100);
        studentProducer.produce(234, "Sita", "spark");
        Thread.sleep(100);
        studentProducer.produce(345, "Lakshmana", "flink");
        Thread.sleep(100);

    }
}
