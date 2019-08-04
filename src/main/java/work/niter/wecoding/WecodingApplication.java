package work.niter.wecoding;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableAsync
@EnableRabbit
@EnableTransactionManagement
@SpringBootApplication
public class WecodingApplication extends SpringBootServletInitializer {
    public WecodingApplication() {
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WecodingApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WecodingApplication.class, args);
    }
}