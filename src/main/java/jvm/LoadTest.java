package jvm;

public class LoadTest {
    public static void main(String args[]) throws ClassNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);

        // 使用ClassLoader.loadClass()来加载类，不会执行初始化块
        loader.loadClass("jvm.TestClassLoad");

        // 使用Class.forName()来加载类，默认会执行初始化块
        Class.forName("jvm.TestClassLoad");

        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("jvm.TestClassLoad", false, loader);
    }
}
