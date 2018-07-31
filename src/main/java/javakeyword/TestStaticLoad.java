package javakeyword;

public class TestStaticLoad {
    Person person = new Person("TestStaticLoad");
    static{
        System.out.println("TestStaticLoad static");
    }

    public TestStaticLoad() {
        System.out.println("TestStaticLoad constructor");
    }

    public static void main(String[] args) {
        new God();
    }

}

class Person{
    static{
        System.out.println("person static");
    }
    public Person(String str) {
        System.out.println("person "+str);
    }
}


class God extends TestStaticLoad {
    Person person = new Person("God");
    static{
        System.out.println("God static");
    }

    public God() {
        System.out.println("God constructor");
    }
}