package work.niter.wecoding.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:04
 * @Description:
 */
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> {
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }
}

