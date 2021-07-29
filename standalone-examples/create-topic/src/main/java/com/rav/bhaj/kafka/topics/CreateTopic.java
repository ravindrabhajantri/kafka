package com.rav.bhaj.kafka.topics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTopic {

	private final static Logger LOGGER = LoggerFactory.getLogger(CreateTopic.class);
	private String topicName;
	private int topicPartition;
	private short topicReplication;
	private AdminClient kafkaAdministrationClient = null;

	public CreateTopic(String topicName, int topicPartition, short topicReplication) {
		this.topicName = topicName;
		this.topicPartition = topicPartition;
		this.topicReplication = topicReplication;
	}

	public static void main(String[] args) {

		CreateTopic createsTopic = new CreateTopic("test-topic", 1, (short) 1);
		createsTopic.create();
	}

	public void create() {
		if (null == kafkaAdministrationClient)
			createKafkaAdministrationClient();

		try {
			if (!kafkaAdministrationClient.listTopics().names().get().contains(topicName)) {
				kafkaAdministrationClient.createTopics(getTopicsToCreate(topicName));
				LOGGER.info("Created topic with  name: {}, partitions: {}, replication: {}", topicName, topicPartition,
						topicReplication);
			} else {
				LOGGER.info("Topic with name {} already exists", topicName);
			}
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Failed to create topic {} : {}", topicName, e.getMessage());
		}
	}

	private List<NewTopic> getTopicsToCreate(String topicName) {
		List<NewTopic> newTopicsToCreate = new ArrayList<NewTopic>();
		newTopicsToCreate.add(getTmTaskTopic(topicName));
		return newTopicsToCreate;
	}

	private NewTopic getTmTaskTopic(String topicName) {
		Map<String, String> topicConfig = getTopicConfigurations();
		final NewTopic newTopic = new NewTopic(topicName, topicPartition, topicReplication);
		newTopic.configs(topicConfig);
		return newTopic;
	}

	private void createKafkaAdministrationClient() {
		Properties kafkaProperties = getKafkaProperties();
		kafkaAdministrationClient = AdminClient.create(kafkaProperties);
	}

	private Map<String, String> getTopicConfigurations() {
		Map<String, String> topicConfig = new HashMap<String, String>();
		topicConfig.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2");
		topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "-1");
		return topicConfig;
	}

	private Properties getKafkaProperties() {
		Properties kafkaProperties = new Properties();
		kafkaProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProperties.put(AdminClientConfig.RETRIES_CONFIG, 3);
		return kafkaProperties;
	}
}
