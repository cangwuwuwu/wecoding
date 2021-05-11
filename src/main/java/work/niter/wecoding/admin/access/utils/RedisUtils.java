package work.niter.wecoding.admin.access.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author CangWu
 * @date 2020/4/11 14:56
 * @description
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 计数，将独立用户访问量(UV)记录在Redis中
     * @param key
     * @param value
     * @return
     */
    public void add(String key, Long value){
        if (redisTemplate.opsForHyperLogLog().size(key) == 0){
            redisTemplate.opsForHyperLogLog().add(key, value);
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
        }else {
            redisTemplate.opsForHyperLogLog().add(key, value);
        }

    }

    /**
     * 删除缓存
     * @param key
     */
    public void del(String... key){
        for (String s : key) {
            redisTemplate.opsForHyperLogLog().delete(s);
        }

    }

    /**
     * 获取总数
     * @param key
     * @return
     */
    public Long size(String key){
        return redisTemplate.opsForHyperLogLog().size(key).longValue();
    }

    /**
     * 用户访问后新增1个访问量到redis中
     * @param key
     *
     */
    public void setUserAccess(String key){
        Object accessCount = redisTemplate.opsForValue().get(key);
        if (accessCount == null){
            accessCount = 1L;
            redisTemplate.opsForValue().set(key, accessCount);
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
        }else {
            redisTemplate.opsForValue().increment(key);
        }

    }

    /**
     * 获取访问量
     * @param key
     * @return
     */
    public Long getUserAccess(String key){
        Object accessCount = redisTemplate.opsForValue().get(key);
        if (accessCount == null){
            accessCount = "0";
        }
        return Long.valueOf(accessCount.toString());
    }

    /**
     * 删除访问量
     * @param key
     */
    public void delUserAccess(String... key){
        for (String s : key) {
            redisTemplate.delete(s);
        }

    }


}
