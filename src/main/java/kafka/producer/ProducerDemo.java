package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import springBoot.modal.vo.UserVo;

import java.util.Properties;

/**
 * @author tangj
 * @date 2018/7/29 20:15
 */
public class ProducerDemo {
    static Properties properties = new Properties();

    static String topic = "user";

    static Producer<String, String> producer = null;

    static {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.44.128:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);
    }

    public static void main(String args[]) {
        ProducerRecord<String, String> record = null;
        try {
            UserVo userVo = new UserVo();
            userVo.setUsername("test");
            userVo.setUid(1);
            userVo.setPassword("123123");

            record = new ProducerRecord<String, String>(topic, null, System.currentTimeMillis(), userVo.getUid().toString(), userVo.toString());
            producer.send(record);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
