package com.rav.bhaj.kafka.avro;

import com.rav.bhaj.kafka.avro.model.Employee;
import com.rav.bhaj.kafka.avro.serializers.ByteToAvroDeserializer;
import com.rav.bhaj.kafka.objects.serializers.BytesToObjectDeserializer;
import com.rav.bhaj.kafka.strings.StringConsumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

import static java.util.Arrays.asList;

public class EmployeeConsumer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(StringConsumer.class);
    private static final boolean KEEP_ON_RUNNING = true;
    private static final String EMPLOYEE_TOPIC_NAME = "EMPLOYEE_TOPIC";
    private Consumer<String, Employee> consumer;

    public void run() {

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                ByteToAvroDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "avro-consumers");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        //consumer = new KafkaConsumer<String, Employee>(consumerProperties);
        //Explicitly passing the deserializers if you have a generic class of serializers
        consumer = new KafkaConsumer<String, Employee>(consumerProperties, new StringDeserializer(), new ByteToAvroDeserializer<>(Employee.class));
        consumer.subscribe(asList(EMPLOYEE_TOPIC_NAME));
        log.info("{} Topic subscription completed", EMPLOYEE_TOPIC_NAME);

        try {
            while (KEEP_ON_RUNNING) {
                ConsumerRecords<String, Employee> employee= consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Employee> employeeRecord : employee) {
                    log.info("Received record from topic => {} with values:\n", employeeRecord.topic());
                    log.info("key => {}\n", employeeRecord.key());
                    log.info("AORMessage received => {}\n", employeeRecord.value().getEmployeeName());
                }
            }
        } catch (WakeupException e) {
            log.error("Stopping {} topic consumer", EMPLOYEE_TOPIC_NAME);
        } finally {
            consumer.close();
        }
    }
}
