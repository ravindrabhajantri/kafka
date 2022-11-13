package com.rav.bhaj.kafka.avro;

import com.rav.bhaj.kafka.objects.StudentConsumer;
import com.rav.bhaj.kafka.objects.StudentProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.rav.bhaj.kafka.avro.TestRunner.class);

    public static void main(String[] args) throws InterruptedException {

        LOGGER.info("Starting Consumer");
        Thread employeeConsumerThread = new Thread(new EmployeeConsumer());
        employeeConsumerThread.start();

        LOGGER.info("Calling producer to send student objects");
        EmployeeProducer employeeProducer = new EmployeeProducer();
        employeeProducer.produce(123, "Rama","kafka");
        Thread.sleep(100);
        employeeProducer.produce(234, "Sita", "spark");
        Thread.sleep(100);
        employeeProducer.produce(345, "Lakshmana", "flink");
        Thread.sleep(100);

    }
}
