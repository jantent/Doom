package jvm;

public class InstanceClass extends ParentClass{

    public static String subStaticField = "子类静态变量";
    public String subField = "子类非静态变量";
    public static StaticClass staticClass = new StaticClass("子类");

    static {
        System.out.println("子类 静态块初始化");
    }

    {
        System.out.println("子类 [非]静态块初始化");
    }

    public InstanceClass(){
        System.out.println("子类构造器初始化");
    }

    public static void main(String args[]) throws InterruptedException {
//        new InstanceClass();
        String a = "";
        System.out.println(a==null);
    }

}

class ParentClass{
    public static String parentStaticField = "父类静态变量";
    public String parentField = "父类[非]静态变量";
    public static StaticClass staticClass = new StaticClass("父类");

    static {
        System.out.println("父类 静态块初始化");
    }

    {
        System.out.println("父类 [非]静态块初始化");
    }

    public ParentClass(){
        System.out.println("父类  构造器初始化");
    }
}

class StaticClass{
    public StaticClass(String name){
        System.out.println(name+" 静态变量加载");
    }
}