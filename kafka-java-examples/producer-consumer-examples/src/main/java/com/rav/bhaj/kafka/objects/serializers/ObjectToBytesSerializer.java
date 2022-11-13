package com.rav.bhaj.kafka.objects.serializers;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ObjectToBytesSerializer<T> implements Serializer<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectToBytesSerializer.class);

	public ObjectToBytesSerializer() {
	}

	@Override
	public void close() {
		// No-op
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// No-op
	}

	@Override
	public byte[] serialize(String arg0, T data) {
		byte[] retVal = null;

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(data);
			objectOutputStream.flush();
			retVal = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			LOGGER.error("Error in serializing the {} object to bytes", data.getClass() ,e);
		}

		return retVal;
	}
}