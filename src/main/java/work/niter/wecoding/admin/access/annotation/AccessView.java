package work.niter.wecoding.admin.access.annotation;

import java.lang.annotation.*;

/**
 * @author CangWu
 * @date 2020/4/11 12:30
 * @description 自定义注解，用来统计访问量
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessView {
    /**
     * 描述
     */
    String description()  default "";
}
