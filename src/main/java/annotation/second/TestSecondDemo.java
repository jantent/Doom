package annotation.second;

/**
 * @author tangj
 * @date 2018/3/3 21:20
 */
public class TestSecondDemo {
    public static void main(String args[]){
        User user = new User();
        user.setAge("liang");
        user.setAge("1");
        System.out.println(UserCheck.check(user));
    }
}
