package work.niter.wecoding.admin.access.annotation;

import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.*;

/**
 * @Author xiaozhai
 * @Date 2020/4/11 12:30
 * @Description: 自定义注解，用来统计访问量
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
