package annotation.firstdemo;

/**
 * @author tangj
 * @date 2018/3/3 20:30
 */
public class User {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    @Init(value = "liang")
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    @Init(value = "25")
    public void setAge(String age) {
        this.age = age;
    }
}
