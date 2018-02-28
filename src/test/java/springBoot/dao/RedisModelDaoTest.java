package springBoot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springBoot.modal.vo.RedisModel;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisModelDaoTest {

    @Resource
    private RedisModelDao redisModelDao;

    @Test
    public void testSet(){
        RedisModel redisModel = new RedisModel("1","adada0",10);
        redisModelDao.save(redisModel);
    }

    @Test
    public void testGet(){
       RedisModel redisModel = redisModelDao.get();
        System.out.println(redisModel);
    }

    @Test
    public void testSetString(){
       String key = "aaa";
       String value = "bbb";
       redisModelDao.setString(key,value);
    }

    @Test
    public void testgetString(){
        String redisModel = redisModelDao.getString("aaa");
        System.out.println(redisModel);
    }
}