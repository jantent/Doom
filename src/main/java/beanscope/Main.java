package beanscope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String args[]){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);

        DemoSingletonService singleton1 = context.getBean(DemoSingletonService.class);
        DemoSingletonService singleton2 = context.getBean(DemoSingletonService.class);

        DemoPrototypeService prototype1 = context.getBean(DemoPrototypeService.class);
        DemoPrototypeService prototype2 = context.getBean(DemoPrototypeService.class);

        System.out.println("s1与s2是否相等: "+singleton1.equals(singleton1));
        System.out.println("p1与p2是否相等: "+prototype1.equals(prototype2));

        context.close();
    }
}
