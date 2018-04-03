package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy {
    public static void main(String args[]) throws Exception{
        MyInvocationHandler demo = new MyInvocationHandler();
        Subject subject = (Subject) demo.bind(new RealSubject());
        System.out.println(subject.say("janti",20));
    }
}

interface Subject{
    public String say(String name, int age);
}

// 定义真实项目
class RealSubject implements Subject {
    public String say(String name, int age) {
        return name + "  " + age;
    }
}

//如果想要完成动态代理，首先需要定义一个InvocationHandler接口的子类，已完成代理的具体操作。

class MyInvocationHandler implements InvocationHandler{

    private Object object = null;
    public Object bind(Object obj){
        this.object = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object temp = method.invoke(this.object,args);
        return temp;
    }
}