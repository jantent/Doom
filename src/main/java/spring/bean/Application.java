package spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tangj
 * @date 2018/9/4 23:14
 */
public class Application {
    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanTest.class);
        BeanTest beanTest = (BeanTest) context.getBean("getBean");
        System.out.println(beanTest);
    }
}
