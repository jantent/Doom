package kafka.producer;

import kafka.OrderMessage;
import kafka.partition.PartitionUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

/**
 * kafka生产者
 */
public class ProducerSimpleDemo {
    static Properties properties = new Properties();

    //主题名称
    static String topic = "MyOrder";

    //生产者
    static KafkaProducer<String, String> producer = null;

    //生产者配置
    static {
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.90.53:9092");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, PartitionUtil.class.getName());
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    public static void main(String args[]) throws Exception {
        sendMsg();
    }

    /**
     * 发送消息
     *
     * @throws Exception
     */
    public static void sendMsg() throws Exception {
        ProducerRecord<String, String> record = null;
        try {
            // 循环发送一百条消息
            for (int i = 0; i < 10; i++) {
                // 构造待发送的消息
                OrderMessage orderMessage = new OrderMessage();
                orderMessage.setId(UUID.randomUUID().toString());
                long timestamp = System.nanoTime();
                orderMessage.setCreateTime(timestamp);
                orderMessage.setRemake("remind");
                orderMessage.setsName("test");
                // 实例化ProducerRecord
                record = new ProducerRecord<String, String>(topic, timestamp + "", orderMessage.toString());
                producer.send(record, (metadata, e) -> {
                    // 使用回调函数
                    if (null != e) {
                        e.printStackTrace();
                    }
                    if (null != metadata) {
                        System.out.println(String.format("offset: %s, partition:%s, topic:%s  timestamp:%s", metadata.offset(), metadata.partition(), metadata.topic(), metadata.timestamp()));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
