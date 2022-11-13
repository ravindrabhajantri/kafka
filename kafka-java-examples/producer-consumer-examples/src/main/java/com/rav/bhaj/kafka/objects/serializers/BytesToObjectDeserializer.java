package com.rav.bhaj.kafka.objects.serializers;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Map;

public class BytesToObjectDeserializer<T> implements Deserializer<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BytesToObjectDeserializer.class);

	protected Class<T> targetType = null;

	public BytesToObjectDeserializer(Class<T> targetType) {
		super();
		this.targetType = targetType;
	}

	public BytesToObjectDeserializer() {
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
	public T deserialize(String topic, byte[] data) {
		try {
			T result = null;
			try {
				 result = (T) getObject(data);
			} catch (Exception e) {
				LOGGER.error("Error in deserialization", e);
			}
			return result;
		} catch (Exception ex) {
			throw new SerializationException(
					"Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
		}
	}

	private Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
		ObjectInput in = new ObjectInputStream(bis);
		return in.readObject();
	}
}