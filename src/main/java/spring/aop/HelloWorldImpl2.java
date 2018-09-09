package spring.aop;

import org.springframework.stereotype.Component;

/**
 * @author tangj
 * @date 2018/9/9 17:52
 */
@Component("hw2")
public class HelloWorldImpl2 implements HelloWorld{
    @Override
    public void printHelloWorld() {
        System.out.println("2222  printHelloWorld");
    }

    @Override
    public void doPrint() {
        System.out.println("2222 doPrint");
    }
}
