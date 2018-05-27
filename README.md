kafka stream analysis demo

#create topic 
bin/kafka-topics.sh --zookeeper zookeeper0:12181/kafka --topic items  --create --partitions 3 --replication-factor 1
bin/kafka-topics.sh --zookeeper zookeeper0:12181/kafka --topic orders  --create --partitions 3 --replication-factor 1
bin/kafka-topics.sh --zookeeper zookeeper0:12181/kafka --topic users  --create --partitions 3 --replication-factor 1

bin/kafka-topics.sh --zookeeper zookeeper0:12181/kafka --topic orderuser-by-item  --create --partitions 3 --replication-factor 1
bin/kafka-topics.sh --zookeeper zookeeper0:12181/kafka --topic orderuseritem-by-category --create --partitions 3 --replication-factor 1
