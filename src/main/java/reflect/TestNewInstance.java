package reflect;

import java.lang.reflect.Constructor;

/**
 * @author tangj
 * @date 2018/4/2 22:39
 */
public class TestNewInstance {
    public static void main(String[] args) throws Exception{
        Class<?> class1 = null;
        class1 = Class.forName("reflect.User");
        // 第一种方法，实例化默认构造方法，调用set赋值
        User user = (User)class1.newInstance();
        user.setAge(20);
        user.setName("adf");
        System.out.println(user);
        // 第二种 取得全部的构造函数 使用构造函数赋值

    }
}

class User {
    private int age;
    private String name;
    public User() {
        super();
    }
    public User(String name) {
        super();
        this.name = name;
    }
    public User(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "User [age=" + age + ", name=" + name + "]";
    }
}