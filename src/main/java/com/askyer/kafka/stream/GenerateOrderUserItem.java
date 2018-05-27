package com.askyer.kafka.stream;

import com.askyer.kafka.stream.model.Item;
import com.askyer.kafka.stream.model.Order;
import com.askyer.kafka.stream.model.User;
import com.askyer.kafka.stream.model.OrderUser;
import com.askyer.kafka.stream.model.OrderUserItem;
import com.askyer.kafka.stream.model.OrderStats;
import com.askyer.kafka.stream.serdes.SerdesFactory;
import com.askyer.kafka.stream.model.OrderTimestampExtractor;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.io.IOException;
import java.util.Properties;

public class GenerateOrderUserItem {

	public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "windows-analysis_17");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka0:19092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, OrderTimestampExtractor.class);

        KStreamBuilder streamBuilder = new KStreamBuilder();
        KStream<String, Order> orderStream = streamBuilder.stream(Serdes.String(), SerdesFactory.serdFrom(Order.class), "orders");
        KTable<String, User> userTable = streamBuilder.table(Serdes.String(), SerdesFactory.serdFrom(User.class), "users", "users-state-store");
        KTable<String, Item> itemTable = streamBuilder.table(Serdes.String(), SerdesFactory.serdFrom(Item.class), "items", "items-state-store");

		KStream<String, OrderUserItem> kStream = orderStream
                .leftJoin(userTable, (Order order, User user) -> OrderUser.fromOrderUser(order, user), Serdes.String(), SerdesFactory.serdFrom(Order.class))
                .filter((String userName, OrderUser orderUser) -> orderUser.getUser_address() != null)
                .map((String userName, OrderUser orderUser) -> new KeyValue<String, OrderUser>(orderUser.getItem_name(), orderUser))
				.through(Serdes.String(), SerdesFactory.serdFrom(OrderUser.class), (String key, OrderUser orderUser, int numPartitions) -> (orderUser.getItem_name().hashCode() & 0x7FFFFFFF) % numPartitions, "orderuser-by-item")
                .leftJoin(itemTable, (OrderUser orderUser, Item item) -> OrderUserItem.fromOrderUser(orderUser, item), Serdes.String(), SerdesFactory.serdFrom(OrderUser.class))
                .filter((String item_name, OrderUserItem orderUserItem) -> orderUserItem.getAge() >=18 &&  orderUserItem.getAge() <= 35)
                .map((String item_name, OrderUserItem orderUserItem) -> new KeyValue<String, OrderUserItem>(orderUserItem.getCategory(), orderUserItem))
		        .through(Serdes.String(), SerdesFactory.serdFrom(OrderUserItem.class), (String key, OrderUserItem orderUserItem, int numPartitions) -> (orderUserItem.getCategory().hashCode() & 0x7FFFFFFF) % numPartitions, "orderuseritem-by-category");
		kStream.to(Serdes.String(), SerdesFactory.serdFrom(OrderUserItem.class), "demo");

        kStream.foreach((String category, OrderUserItem orderUserItem) -> System.out.printf("%s-%s\n", category, orderUserItem));

        KafkaStreams kafkaStreams = new KafkaStreams(streamBuilder, props);
        kafkaStreams.cleanUp();
        kafkaStreams.start();

        //System.in.read();
        Thread.sleep(60000L);
        kafkaStreams.close();
	}




}
