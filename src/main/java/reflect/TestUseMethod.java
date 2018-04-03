package reflect;

import java.lang.reflect.Method;

public class TestUseMethod {
    public static void main(String[] args)throws Exception{
        Class<?> clazz = Class.forName("reflect.TestUseMethod");
        // 调用reflect1方法
        Method method = clazz.getMethod("reflect1");
        method.invoke(clazz.newInstance());

        // 调用reflect2方法
        method = clazz.getMethod("reflect2", int.class, String.class);
        method.invoke(clazz.newInstance(),20,"test");
    }

    public void reflect1() {
        System.out.println("Java 反射机制 - 调用某个类的方法1.");
    }
    public void reflect2(int age, String name) {
        System.out.println("Java 反射机制 - 调用某个类的方法2.");
        System.out.println("age -> " + age + ". name -> " + name);
    }
}
