package classload;

/**
 * @author tangj
 * @date 2018/3/26 22:58
 */
public class TestClassLoadDemo {
    public static void main(String[] args){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }
}
