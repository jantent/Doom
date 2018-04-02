package annotation.firstdemo;

/**
 * @author tangj
 * @date 2018/3/3 20:43
 */
public class TestUserAnnotationDemo {
    public static void main(String args[]){
        User user = UserFactory.create();
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }
}
