package spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tangj
 * @date 2018/9/9 18:10
 */
public class MainApp {
    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorld hw1 = (HelloWorld)context.getBean("hw1");
        HelloWorld hw2 = (HelloWorld)context.getBean("hw2");

        hw1.printHelloWorld();
        hw2.doPrint();
    }
}
