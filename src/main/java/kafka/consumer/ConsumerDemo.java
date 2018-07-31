package kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumerDemo {
    static Properties properties = new Properties();

    private static String topic = "consumer";

    private static long polltimeout = 1000;

    static {
        // bootstarp server 地址
        properties.put("bootstrap.servers", "10.0.90.53:9092");
//        //group.id指定消费者，所在的组
        properties.put("group.id", "consumer");
//        //
        properties.put("client.id", "consumer");

        // 在没有指定消费偏移量提交方式时，默认是每个1s提交一次偏移量，可以通过auto.commit.interval.ms参数指定提交间隔
        // 自动提交要设置成false
        // 手动提交设置成true
        properties.put("enable.auto.commit", false);
//        properties.put("auto.commit.interval.ms", 1000);

        // key序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value序列化
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


    }

    public static void main(String args[]) {
//        consumeAutoCommit();
//        consumeHandleCommit();
        consumeByTimestamp();
    }

    public static void consumeHandleCommit() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        try {
            int maxcount = 5;
            int count = 0;
            kafkaConsumer.subscribe(Arrays.asList(topic));
            for (; ; ) {
                // 拉取消息
                ConsumerRecords<String, String> records = kafkaConsumer.poll(polltimeout);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("收到消息：          " + String.format("partition = %d, offset= %d, key=%s, value=%s%n",
                            record.partition(), record.offset(), record.key(), record.value()));
                    count++;
                }
                // 业务逻辑完成后，提交偏移量
                if (count >= maxcount) {
                    kafkaConsumer.commitAsync(new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                            if (null != exception) {
                                exception.printStackTrace();
                            } else {
                                System.out.println("偏移值提交成功");
                            }
                        }
                    });
                    count=0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }


    }

    /**
     * 消费消息自动提交偏移值
     */
    public static void consumeAutoCommit() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topic));

        try {
            for (; ; ) {
                // 拉取消息
                ConsumerRecords<String, String> records = kafkaConsumer.poll(polltimeout);
                for (ConsumerRecord<String, String> record : records) {
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

    public static void consumeByTimestamp(){
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.assign(Arrays.asList(new TopicPartition(topic,0)));
        try {
            Map<TopicPartition,Long> topicPartitionLongMap = new HashMap<>();
            // 构造待查询的分区
            TopicPartition partition = new TopicPartition(topic,0);
            // 设置查询12小时之前的偏移值
            topicPartitionLongMap.put(partition,(System.currentTimeMillis() - 12*3600*1000));
            // 会返回时间大于等于查找时间的第一个偏移量
            Map<TopicPartition,OffsetAndTimestamp>offsetMap = kafkaConsumer.offsetsForTimes(topicPartitionLongMap);
            OffsetAndTimestamp offsetAndTimestamp = null;
            for (Map.Entry<TopicPartition,OffsetAndTimestamp> entry:offsetMap.entrySet()){
                offsetAndTimestamp = entry.getValue();
                if (null != offsetAndTimestamp){
                    kafkaConsumer.seek(partition,entry.getValue().offset());
                }
            }

            while (true){
                // 等待拉取消息
                ConsumerRecords<String,String> records = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("收到消息：          " + String.format("partition = %d, offset= %d, key=%s, value=%s%n",
                            record.partition(), record.offset(), record.key(), record.value()));
                }
            }

        }catch (Exception e){

        }finally {

        }
    }
}
