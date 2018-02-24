package applicationevent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoMain {
    public static void main(String args[]){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher demoPublisher = context.getBean(DemoPublisher.class);
        demoPublisher.publish("hello application event");
        context.close();
    }
}
