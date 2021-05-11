package work.niter.wecoding.admin.access.annotation;

import java.lang.annotation.*;

/**
 * @author CangWu
 * @date 2020/4/19 10:59
 * @description
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageView {
    String value();
}
