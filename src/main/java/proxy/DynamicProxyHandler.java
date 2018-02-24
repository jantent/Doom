package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;
    public DynamicProxyHandler(Object proxied){
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(" ****proxy:"+proxy.getClass()+",method: "+method+", args:"+args);

        return method.invoke(proxied,args);
    }
}

class SimpleDynamicProxy{
    public static void consumer(myInterface iface){
        iface.doSomething();
        iface.doSomethingElse("bnonoo");
    }
    public static void main(String[] args){
        RealObject real = new RealObject();
        consumer(real);
        myInterface proxy = (myInterface) Proxy.newProxyInstance(myInterface.class.getClassLoader(),new Class[]{myInterface.class},new DynamicProxyHandler(real));
        consumer(proxy);
    }
}
