package annotation.second;

import java.lang.annotation.*;

/**
 * @author tangj
 * @date 2018/3/3 20:51
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    public int min() default 1;

    public int max() default 10;

    public boolean isNotNull() default true;
}
