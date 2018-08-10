package kafka.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerExcutor {


    /**
     * kafkaConsumer是非线程安全的，处理好多线程同步的方案是
     * 每个线程实例化一个kafkaConsumer对象
     */
    public static void main(String args[]) {

        String topic = "hello";

        Map<String, Object> configMap = new HashMap<>();
        configMap.put("bootstrap.servers", "10.0.90.53:9092");
        //group.id指定消费者，所在的组,保证所有线程都在一个消费者组
        configMap.put("group.id", "test");
        configMap.put("enable.auto.commit", true);
        configMap.put("auto.commit.interval.ms", 1000);

        // key序列化
        configMap.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value序列化
        configMap.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        ExecutorService service = Executors.newFixedThreadPool(6);
        // 该主题总共有6个分区，那么保证6个线程
        for (int i = 0; i < 6; i++) {
            service.submit(new KafkaConsumerThread(configMap, topic));
        }
    }
}
