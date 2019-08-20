package work.niter.wecoding;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCaching
@EnableAsync
@EnableRabbit
@MapperScan(basePackages = "work.niter.wecoding.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class WecodingApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WecodingApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WecodingApplication.class, args);
    }
}