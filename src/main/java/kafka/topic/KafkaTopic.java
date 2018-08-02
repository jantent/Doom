package kafka.topic;

import kafka.admin.AdminUtils;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author tangj
 * @date 2018/7/29 19:34
 */
public class KafkaTopic {

    // 连接配置
    private static final String ZK_CONNECT = "10.0.90.53:2181";

    // session过期时间
    private static final int SEESSION_TIMEOUT = 30 * 1000;

    // 连接超时时间
    private static final int CONNECT_TIMEOUT = 30 * 1000;

    public static void main(String args[]) {
        String topic  = "MyOrder";
        createTopic(topic, 6, 1, new Properties());
//        searchTopic(topic);
//        deleteTopic(topic);
//        Properties props = new Properties();
//        // 删除topic级别属性
//        props.remove("max.message.bytes");
//        modifyTopic(topic,props);
    }


    /**
     * 创建主题
     *
     * @param topic 主题名称
     * @param partition 分区数
     * @param repilca 副本数
     * @param properties 配置信息
     */
    public static void createTopic(String topic, int partition, int repilca, Properties properties) {
        ZkUtils zkUtils = null;
        try {
            // 创建zkutil
            zkUtils = ZkUtils.apply(ZK_CONNECT, SEESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            if (!AdminUtils.topicExists(zkUtils, topic)) {
                //主题不存在，则创建主题
                AdminUtils.createTopic(zkUtils, topic, partition, repilca, properties, AdminUtils.createTopic$default$6());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkUtils.close();
        }
    }

    public static void  searchTopic(String topic){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(ZK_CONNECT, SEESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            // 获取topic属性
            Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(),topic);
            // 查询topic-level属性
            Iterator it = props.entrySet().iterator();
            System.out.println("---------------------");
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key+" = "+value);
            }
            System.out.println("---------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkUtils.close();
        }
    }

    /**
     * 删除主题
     *
     * @param topic
     */
    public static void deleteTopic(String topic){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(ZK_CONNECT, SEESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            AdminUtils.deleteTopic(zkUtils,topic);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkUtils.close();
        }
    }

    public static void modifyTopic(String topic,Properties properties){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(ZK_CONNECT, SEESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            // 首先获取当前已有的配置，这里查询主题级别的配置，因此指定配置类型为Topic
            Properties curProp = AdminUtils.fetchEntityConfig(zkUtils,ConfigType.Topic(),topic);
            curProp.putAll(properties);
            AdminUtils.changeTopicConfig(zkUtils,topic,curProp);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkUtils.close();
        }
    }
}
