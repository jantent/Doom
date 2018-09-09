package spring.aop;

import org.springframework.stereotype.Component;

/**
 * @author tangj
 * @date 2018/9/9 17:40
 */
@Component("hw1")
public class HelloWorldImpl1 implements HelloWorld{
    @Override
    public void printHelloWorld() {
        System.out.println("1111  printHelloWorld");
    }

    @Override
    public void doPrint() {
        System.out.println("1111 doPrint");
    }
}
