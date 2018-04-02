package annotation.second;

/**
 * @author tangj
 * @date 2018/3/3 20:54
 */
public class User {
    @Validate(min = 2, max = 5)
    private String name;

    @Validate(isNotNull = false)
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
