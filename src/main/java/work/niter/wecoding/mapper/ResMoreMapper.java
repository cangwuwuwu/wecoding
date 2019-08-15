package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.niter.wecoding.entity.ResMore;

/**
 * @Author: Cangwu
 * @Date: 2019/8/14 13:50
 * @Description:
 */
@Mapper
public interface ResMoreMapper {

    @Select("select * from resource_more where res_id=#{resId}")
    ResMore selectResMoreById(Integer resId);

}
