package kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 简单的消费者
 *
 * @author tangj
 */
public class KafkaSimpleDemo {
    static Properties properties = new Properties();

    private static String topic = "MyOrder";

    //poll超时时间
    private static long pollTimeout = 2000;

    static {
        // bootstarp server 地址
        properties.put("bootstrap.servers", "10.0.90.53:9092");
        // group.id指定消费者，所在的组
        properties.put("group.id", "order");
        // 组中client 的ID名称
        properties.put("client.id", "consumer");

        // 在没有指定消费偏移量提交方式时，默认是每个1s提交一次偏移量，可以通过auto.commit.interval.ms参数指定提交间隔
        // 自动提交要设置成true
        // 手动提交设置成false
        properties.put("enable.auto.commit", true);
        // 自动提交偏移值的 时间间隔
        properties.put("auto.commit.interval.ms", 2000);

        // key序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value序列化
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public static void main(String args[]) {
        consumeAutoCommit();
    }

    /**
     * 消费消息自动提交偏移值
     */
    public static void consumeAutoCommit() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = kafkaConsumer.poll(pollTimeout);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("消息总数为：  " + records.count());
                    System.out.println("收到消息：          " + String.format("partition = %d, offset= %d, key=%s, value=%s%n",
                            record.partition(), record.offset(), record.key(), record.value()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
    }
}
