package work.niter.wecoding.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:04
 * @Description:
 */
@MapperScan(
        basePackages = {"com.cangwu.wecoding.mapper"}
)
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> {
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }
}

