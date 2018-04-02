package annotation.firstdemo;

import java.lang.reflect.Method;

/**
 * 使用构造工厂充当构造器
 *
 * @author tangj
 * @date 2018/3/3 20:32
 */
public class UserFactory {
    public static User create() {
        User user = new User();

        // 获取User类中的所有方法(getDclareMethods也行)
        Method[] methods = User.class.getMethods();
        try {
            for (Method method : methods) {
                if (method.isAnnotationPresent(Init.class)) {

                    //如果此方法有注解，就把注解里面的数据赋值到user对象
                    Init init = method.getAnnotation(Init.class);
                    method.invoke(user, init.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
