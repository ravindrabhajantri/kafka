package com.rav.bhaj.kafka.objects;

import com.rav.bhaj.kafka.objects.model.Student;
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

public class StudentConsumer implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(StringConsumer.class);
    private static final boolean KEEP_ON_RUNNING = true;
    private static final String STUDENT_TOPIC_NAME = "STUDENT_TOPIC";
    private Consumer<String, Student> consumer;

    public void run() {

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                BytesToObjectDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "object-consumers");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        consumer = new KafkaConsumer<String, Student>(consumerProperties);
        consumer.subscribe(asList(STUDENT_TOPIC_NAME));
        log.info("{} Topic subscription completed", STUDENT_TOPIC_NAME);

        try {
            while (KEEP_ON_RUNNING) {
                ConsumerRecords<String, Student> studentNames = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Student> studentRecord : studentNames) {
                    log.info("Received record from topic => {} with values:\n", studentRecord.topic());
                    log.info("key => {}\n", studentRecord.key());
                    log.info("AORMessage received => {}\n", studentRecord.value().getStudentName());
                }
            }
        } catch (WakeupException e) {
            log.error("Stopping {} topic consumer", STUDENT_TOPIC_NAME);
        } finally {
            consumer.close();
        }
    }
}
