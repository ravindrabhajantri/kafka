# Create Kafka Topic Programmatically using Java
This project implements Kafka Topic creation programmatically.

## Setup

Project is a standalone program:
* Requires Download, Compile and Run.
* Requires a running Kafka instance. Run docker-compose.yaml attached here to run get a Kafka Instance.

## Description
This project implements Kafka Topic creation programmatically. Sample Kafka and Topic creation configuration are demonstrated in the main program for the ease of understanding. Add more configurations if required. When program is run, creates a topic (name can be configured in the program) if not exists. Logs a message accordingly.

Code snapshot:
```
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
```


