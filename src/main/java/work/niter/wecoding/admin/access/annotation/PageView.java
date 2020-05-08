package work.niter.wecoding.admin.access.annotation;

import java.lang.annotation.*;

/**
 * @Author xiaozhai
 * @Date 2020/4/19 10:59
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageView {
    String value();
}
