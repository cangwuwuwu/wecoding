package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.*;
import work.niter.wecoding.entity.Resource;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/7 1:14
 * @Description:
 */
@Mapper
public interface ResourceMapper {

    @Select("select * from resources where instr(res_type,#{resType})")
    @Results({
            @Result(id = true, property = "resId", column = "res_id"),
            @Result(property = "resMore", column = "res_id",
            one = @One(select = "work.niter.wecoding.mapper.ResMoreMapper.selectResMoreById"))
    })
    List<Resource> getLanguageRes(String resType);

}
