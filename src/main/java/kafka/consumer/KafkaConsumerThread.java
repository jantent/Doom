package kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class KafkaConsumerThread implements Runnable {

    //每个线程私有一个consumer实例

    private KafkaConsumer<String,String> consumer;

    public KafkaConsumerThread(Map<String,Object> configMap,String topic) {
        Properties properties = new Properties();
        properties.putAll(configMap);
        this.consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {

        try {
            for (; ; ) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("收到消息：          " + String.format("partition = %d, offset= %d, key=%s, value=%s%n",
                            record.partition(), record.offset(), record.key(), record.value()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
