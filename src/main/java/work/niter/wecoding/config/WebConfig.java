package work.niter.wecoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import work.niter.wecoding.global.WeLocaleResolver;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:05
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
        registry.addViewController("index").setViewName("index");
        registry.addViewController("signup").setViewName("signup");
        registry.addViewController("em").setViewName("email-code");
        registry.addViewController("home").setViewName("home");
        registry.addViewController("personal").setViewName("personal");
        registry.addViewController("allstus").setViewName("allstus");
        registry.addViewController("guide").setViewName("guide");
        registry.addViewController("help").setViewName("help");
        registry.addViewController("forum").setViewName("forum");
        registry.addViewController("photos").setViewName("photos");
        registry.addViewController("resources").setViewName("resources");
        registry.addViewController("chatroom").setViewName("chatroom");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new WeLocaleResolver();
    }
}
