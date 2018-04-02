package annotation.firstdemo;

import java.lang.annotation.*;

/**
 * Init.java
 *
 * @author tangj
 * @date 2018/3/3 20:23
 */
@Documented
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Init {
    public String value() default "";
}
