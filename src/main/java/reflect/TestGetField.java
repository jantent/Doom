package reflect;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestGetField extends Object {
    private static final long serialVersionUID = -2862585049955236662L;

    public static void main(String args[]) throws Exception {
        Class<?> clazz = Class.forName("reflect.TestGetField");
        System.out.println("===============本类属性===============");
        // 取得本类属性
        Field[] fields = clazz.getDeclaredFields();
        getField(fields);


        System.out.println("==========实现的接口或者父类的属性==========");
        // 取得实现的接口或者父类的属性
        Field[] fatherField = clazz.getFields();
        getField(fatherField);
    }

    public static void getField(Field[] fields) {
        for (Field field : fields) {
            // 权限修饰符
            int mo = field.getModifiers();
            String priv = Modifier.toString(mo);
            Class<?> type = field.getType();
            System.out.println(priv + " " + type.getName() + " " + field.getName() + ";");
        }
    }
}
