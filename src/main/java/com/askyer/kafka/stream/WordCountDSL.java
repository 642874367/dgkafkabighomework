package com.askyer.kafka.stream;

import com.askyer.kafka.stream.model.Order;
import com.askyer.kafka.stream.model.OrderTimestampExtractor;
import com.askyer.kafka.stream.model.OrderUserItem;
import com.askyer.kafka.stream.serdes.SerdesFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import java.util.Arrays;
import java.util.Properties;

public class WordCountDSL {

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wc-ds4");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka0:19092");
		props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181/kafka");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, OrderTimestampExtractor.class);

		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, OrderUserItem> stream = builder.stream(Serdes.String(), SerdesFactory.serdFrom(OrderUserItem.class), "demo");

		KStream<String, String> kStream = stream
		.map((k, v) -> KeyValue.<String, String>pair(v.getCategory(), v.getItem_name())).groupByKey().aggregate(
				() -> 0L,
				(aggKey, value, aggregate) -> aggregate + 1L, 
				TimeWindows.of(3600000).advanceBy(5000),
				Serdes.Long(), 
				"Counts")
		.toStream()
		.map((Windowed<String> window, Long value) -> {
			return new KeyValue<String, String>(window.key(), String.format("key=%s, value=%s, start=%d, end=%d\n",window.key(), value, window.window().start(), window.window().end()));
			});
		kStream.to(Serdes.String(), Serdes.String(), "count");
		kStream.foreach((String category, String desc) -> System.out.printf("%s->%s\n", category, desc));


		KafkaStreams streams = new KafkaStreams(builder, props);
		streams.start();
		Thread.sleep(100000L);
		streams.close();
	}

}
