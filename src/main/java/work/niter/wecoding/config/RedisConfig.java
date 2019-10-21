package work.niter.wecoding.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import work.niter.wecoding.entity.Course;
import work.niter.wecoding.entity.ResWeb;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:04
 * @Description:
 */
@Configuration
public class RedisConfig {

    @Bean
    public CacheManager stuCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
        redisSerializer.setObjectMapper(mapper);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30L)).serializeValuesWith(SerializationPair.fromSerializer(redisSerializer));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, ResWeb> redisTemplate(RedisConnectionFactory factory) {
        //设置序列化
        Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer<>(ResWeb.class);
        // 配置redisTemplate
        RedisTemplate<String, ResWeb> redisTemplate = new RedisTemplate<>();
        return setTemplate(factory, redisTemplate, redisSerializer);
    }

    @Bean
    public RedisTemplate<String, Course> courseRedisTemplate(RedisConnectionFactory factory) {
        //设置序列化
        Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer<>(Course.class);
        // 配置redisTemplate
        RedisTemplate<String, Course> redisTemplate = new RedisTemplate<>();
        return setTemplate(factory, redisTemplate, redisSerializer);

    }

    private RedisTemplate setTemplate(
            RedisConnectionFactory factory, RedisTemplate redisTemplate,
            Jackson2JsonRedisSerializer redisSerializer) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
        redisSerializer.setObjectMapper(mapper);
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}