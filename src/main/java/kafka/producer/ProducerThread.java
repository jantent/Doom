package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 生产者线程
 */
public class ProducerThread implements Runnable {

    private KafkaProducer<String, String> producer = null;
    private ProducerRecord<String, String> record = null;

    public ProducerThread(KafkaProducer<String, String> producer, ProducerRecord<String, String> record) {
        this.producer = producer;
        this.record = record;
    }

    @Override
    public void run() {
        producer.send(record, (metadata, e) -> {
            if (null != e) {
                e.printStackTrace();
            }
            if (null != metadata) {
                System.out.println("消息发送成功 ：         "+String.format("offset: %s, partition:%s, topic:%s  timestamp:%s",
                        metadata.offset(), metadata.partition(), metadata.topic(), metadata.timestamp()));
            }
        });
    }

}

