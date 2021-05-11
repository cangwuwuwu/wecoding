package work.niter.wecoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Cangwu
 * @date 2019/9/15 14:24
 * @description elasticsearch配置类
 */
/*@Configuration
public class ElasticSearchConfig {
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Bean
    public TransportClient client() throws UnknownHostException {
        TransportAddress node = new TransportAddress(
                InetAddress.getByName("119.3.59.217"), 9300);
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();
        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddresses(node);
        return client;
    }
}*/
