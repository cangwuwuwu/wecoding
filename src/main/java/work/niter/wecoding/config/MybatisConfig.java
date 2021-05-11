package work.niter.wecoding.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cangwu
 * @date 2019/7/14 1:04
 * @description
 */
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) ->
                configuration.setMapUnderscoreToCamelCase(true);
    }
}

