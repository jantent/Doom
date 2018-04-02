package reflect;

/**
 * @author tangj
 * @date 2018/4/2 21:59
 */
public class TestReflect {
    public static void main(String args[]){
        TestReflect testReflect = new TestReflect();
        System.out.println(testReflect.getClass().getName());
        System.out.println(testReflect.getClass().getClassLoader());
    }
}
