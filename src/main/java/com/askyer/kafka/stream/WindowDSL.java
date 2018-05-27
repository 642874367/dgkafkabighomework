package com.askyer.kafka.stream;

import com.askyer.kafka.stream.model.OrderTimestampExtractor;
import com.askyer.kafka.stream.model.OrderUserItem;
import com.askyer.kafka.stream.serdes.SerdesFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import com.askyer.kafka.stream.model.OrderStats;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import java.util.Properties;

public class WindowDSL {

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wc-ds16");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka0:19092");
		props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181/kafka");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, OrderTimestampExtractor.class);

		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, OrderUserItem> stream = builder.stream(Serdes.String(), SerdesFactory.serdFrom(OrderUserItem.class), "demo");

		stream.groupByKey().aggregate(
				OrderStats::new,
				(k, v, OrderStats) -> OrderStats.add(v),
				TimeWindows.of(36000000).advanceBy(5000),//每5秒输出过去1小时
				SerdesFactory.serdFrom(OrderStats.class),
				"stats-store2")
				.toStream()
				.mapValues((value) -> value.compute())
				.map((Windowed<String> window, OrderStats value) -> {
                  return new KeyValue<String, String>(window.key(), String.format("key=%s, count=%d, start=%d, end=%d\n",window.key(), value.getCount(), window.window().start(), window.window().end()));
                })
			.print();


		KafkaStreams streams = new KafkaStreams(builder, props);
		streams.start();
		Thread.sleep(100000L);
		streams.close();
	}

}
