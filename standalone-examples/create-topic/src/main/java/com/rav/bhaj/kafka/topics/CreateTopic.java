package com.rav.bhaj.kafka.topics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTopic {

	private static final String TOPIC_NAME = "test-topic";
	private static final int TOPIC_PARTITION = 1;
	private static final short TOPIC_REPLICATION = 1;
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateTopic.class);

	public static void main(String[] args) {

		Properties kafkaProperties = new Properties();
		kafkaProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		kafkaProperties.put(AdminClientConfig.RETRIES_CONFIG, 3);

		Map<String, String> topicConfig = new HashMap<String, String>();
		topicConfig.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2");
		topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "-1");

		AdminClient kafkaAdministrationClient = AdminClient.create(kafkaProperties);

		final NewTopic newTopic = new NewTopic(TOPIC_NAME, TOPIC_PARTITION, TOPIC_REPLICATION);
		newTopic.configs(topicConfig);

		List<NewTopic> newTopicsToCreate = new ArrayList<NewTopic>();
		newTopicsToCreate.add(newTopic);

		ListTopicsResult existingListOfTopics = kafkaAdministrationClient.listTopics();
		Set<String> existingTopics;
		try {
			existingTopics = existingListOfTopics.names().get();
			if (!existingTopics.contains(TOPIC_NAME)) {
				kafkaAdministrationClient.createTopics(newTopicsToCreate);
				LOGGER.info("Created topic with  name: {}, partitions: {}, replication: {}", TOPIC_NAME,
						TOPIC_PARTITION, TOPIC_REPLICATION);
			} else {
				LOGGER.info("Topic with name {} already exists", TOPIC_NAME);
			}
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Failed to create topic {} : {}", TOPIC_NAME, e.getMessage());
		}
	}
}
