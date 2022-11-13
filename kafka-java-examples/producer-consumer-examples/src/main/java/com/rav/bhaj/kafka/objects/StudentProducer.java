package com.rav.bhaj.kafka.objects;

import com.rav.bhaj.kafka.objects.model.Student;
import com.rav.bhaj.kafka.objects.serializers.ObjectToBytesSerializer;
import com.rav.bhaj.kafka.strings.StringProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class StudentProducer {
    private static final Logger log = LoggerFactory.getLogger(StringProducer.class);
    private static final String STUDENT_TOPIC_NAME = "STUDENT_TOPIC";
    private Producer<String, Student> producer;

    public void produce(int id, String name, String subject) throws InterruptedException {
        // Producer Configurations
        Properties producerConfigurations = new Properties();
        producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        producerConfigurations.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        producerConfigurations.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ObjectToBytesSerializer.class.getName());
        producerConfigurations.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        producerConfigurations.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        // Kafka Producer
        producer = new KafkaProducer<String, Student>(producerConfigurations);

        // Create student object
        Student student = new Student(id, name, subject);
        // Kafka producer Record
        ProducerRecord<String, Student> nameToInsert = new ProducerRecord<String, Student>(STUDENT_TOPIC_NAME, student);

        // Kafka topic send
        producer.send(nameToInsert);
        log.info("Name {} sent to topic {}.", nameToInsert.value(), STUDENT_TOPIC_NAME);

    }
}
