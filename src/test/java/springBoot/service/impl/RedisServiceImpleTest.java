package springBoot.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springBoot.service.IRedisService;

import javax.annotation.Resource;

/**
 * @author tangj
 * @date 2018/2/27 23:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImpleTest {

    @Resource
    private IRedisService redisService;

    private String key = "testKey";

    @Test
    public void testGet() {
        String result = redisService.get("myKey");
        System.out.println("redis读取结果为:" + result);
    }

    @Test
    public void testSet(){
        String toSet = "tangj";
        redisService.set(key,toSet);
    }
}
