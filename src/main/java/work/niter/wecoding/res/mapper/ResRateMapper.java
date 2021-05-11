package work.niter.wecoding.res.mapper;

import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.res.entity.ResRate;

/**
 * @author Cangwu
 * @date 2019/11/11 22:49
 * @description
 */
public interface ResRateMapper extends Mapper<ResRate> {

    /**
     * 添加一条评分记录 ，如果已存在，则更新
     * @param rate
     * @return
     */
    @Insert("insert into resource_rate values (null, #{resId}, #{stuId}, #{rate}, null) " +
            "ON DUPLICATE KEY UPDATE stu_id=#{stuId}, rate=#{rate}")
    int updateIfPresent(ResRate rate);
}
