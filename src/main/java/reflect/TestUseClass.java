package reflect;

import java.lang.reflect.Field;

public class TestUseClass {
    private  String property = null;
    public static void main(String args[]) throws Exception{
        Class<?> clazz = Class.forName("reflect.TestUseClass");
        Object object = clazz.newInstance();
        // 可以直接对private属性赋值
        Field field = clazz.getDeclaredField("property");
        field.setAccessible(true);
        field.set(object, "Java反射机制");
        System.out.println(field.get(object));
    }
}
