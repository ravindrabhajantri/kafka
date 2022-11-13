package com.rav.bhaj.kafka.avro.serializers;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Map;

public class ByteToAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ByteToAvroDeserializer.class);

	protected Class<T> targetType = null;

	public ByteToAvroDeserializer(Class<T> targetType) {
		super();
		this.targetType = targetType;
	}

	public ByteToAvroDeserializer() {
	}

	@Override
	public void close() {
		// No-op
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// No-op
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(String topic, byte[] data) {
		try {
			T result = null;

			if (data != null) {
				LOGGER.info("Received data for deserialize : " + DatatypeConverter.printHexBinary(data));

				DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(
						targetType.newInstance().getSchema());
				Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

				result = (T) datumReader.read(null, decoder);
				
			}
			return result;
		} catch (Exception ex) {
			throw new SerializationException(
					"Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
		}
	}
}