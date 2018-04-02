package jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangj
 * @date 2018/3/25 20:25
 */
public class TestRedis {
    private static Jedis jedis = new Jedis("127.0.0.1",6379);

    public static void main(String args[]){
        //testString();
        testMap();
    }

    /**
     * redis 操作字符串
     */
    public static void testString(){
        // 添加数据
        jedis.set("name","test");
        System.out.println(jedis.get("name"));

        // 拼接
        jedis.append("name", "is my lover");
        System.out.println(jedis.get("name"));

        // 删除某个键
        jedis.del("name");
        System.out.println(jedis.get("name"));

        // 设置多个键值对
        jedis.mset("name","liuling","age","23","qq","4567dxxx");
        // 进行加1操作
        jedis.incr("age");
        System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("qq"));

    }

    /**
     * redis 添加map
     */
    public static void testMap(){
        Map<String,String> map = new HashMap<>();
        map.put("name","qqq");
        map.put("age","123");
        map.put("qq","123456");
        jedis.hmset("user",map);

        // 取出user中的name,执行结果：是一个泛型的List
        // 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数。
        List<String> rsMap = jedis.hmget("user","name","age","qq");
        System.out.println(rsMap);

        // 删除map中的某个键值
        jedis.hdel("user","age");
        System.out.println(jedis.hmget("user","age"));//因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user"));//返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value
    }

}
