package spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author tangj
 * @date 2018/9/4 23:12
 */
@Service
public class BeanTest {
    @Bean
    public BeanTest getBean(){
        BeanTest beanTest = new BeanTest();
        System.out.println("生成bean");
        return beanTest;
    }

    @Override
    public String toString() {
        return "BeanTest{};lskfjdgklsdflk;asdjfkl;asdf";
    }
}
