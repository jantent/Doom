package proxy;

public class SimpleProxyDemo {

    public static void consumer(myInterface myInterface){
        myInterface.doSomething();
        myInterface.doSomethingElse("adfadsf");
    }

    public static void main(String args[]){
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
}

class RealObject implements myInterface{
    @Override
    public void doSomething() {
        System.out.println("do something");
    }

    @Override
    public void doSomethingElse(String args) {
        System.out.println("somethingelse"+args);
    }
}

class SimpleProxy implements myInterface{
    private myInterface proxied;
    public SimpleProxy(myInterface proxied){
        this.proxied = proxied;
    }

    @Override
    public void doSomething() {
        System.out.println("proxy dosomething");
        proxied.doSomething();
    }

    @Override
    public void doSomethingElse(String args) {
        System.out.println("proxy dosomethingelse");
        proxied.doSomethingElse(args);
    }
}