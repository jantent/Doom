package reflect;

/**
 * @author tangj
 * @date 2018/4/2 22:29
 */
public class TestInstance {
    public TestInstance(){
        System.out.println("实例化");
    }

    public static void main(String args[]) throws Exception{
        Class<?> class1 = null;
        Class<?> class2 = null;
        Class<?> class3 = null;

        class1 = Class.forName("reflect.TestInstance");
        Object object = class1.newInstance();
        class2 = new TestInstance().getClass();
        class3 = TestInstance.class;
        System.out.println("类名称   " + class1.getName());
        System.out.println("类名称   " + class2.getName());
        System.out.println("类名称   " + class3.getName());
    }
}
