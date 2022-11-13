/opt/bitnami/kafka/bin/kafka-console-produer.sh  --bootstrap-server kafka:9092 --topic test-topic


# Sample Kafka Producers and Consumers
This project implements sample Kafka Producers and Consumers.

## Setup

Project is a stand-alone program:
* Requires Download, Compile and Run.
* Requires a running Kafka instance. Run docker-compose.yaml attached here to run get a Kafka Instance.

## Description
This project implements Kafka Producers and Consumers. The implementations are grouped at different packages. TestRunner class is provided at the respective package to test the working of the Producers and Consumers.

## Sample Producers and Consumers implemented

* Strings: Producers and Consumers exchange string messages
* POJO: Producers and Consumers exchange simple plain Java objects
