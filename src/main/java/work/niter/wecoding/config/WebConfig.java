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

    @Bean
    public LocaleResolver localeResolver() {
        return new WeLocaleResolver();
    }
}
