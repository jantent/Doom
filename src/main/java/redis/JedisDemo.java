package redis;

import redis.clients.jedis.Jedis;

public class JedisDemo {

    private static String stringKey = "myStringKey";

    public static void main(String args[]) {
        // 本地的redis服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        // 查看服务是否正常运行
        System.out.println("服务正在运行:" + jedis.ping());
        setString(jedis, stringKey, "adsfas");
        getResult(jedis, stringKey);
    }

    /**
     * redis设置字符串
     *
     * @param jedis
     * @param key
     * @param value
     */
    public static void setString(Jedis jedis, String key, String value) {
        // 设置jedis字符串
        jedis.set(key, value);
    }

    /**
     * @param jedis
     * @param key
     */
    public static void getResult(Jedis jedis, String key) {
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get(key));
    }
}
