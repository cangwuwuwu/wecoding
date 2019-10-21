package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.entity.ResWeb;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/18 19:31
 * @Description:
 */
public interface ResWebMapper extends Mapper<ResWeb> {

    @Select("select * from resource_web where instr(res_web_type,#{resWebType})")
    @Results(id="web_result_map", value = {
            @Result(id = true, property = "Id", column = "id"),
            @Result(property = "resWebType", column = "res_web_type"),
            @Result(property = "resWebName", column = "res_web_name"),
            @Result(property = "resWebDescribe", column = "res_web_describe"),
            @Result(property = "resWebUrl", column = "res_web_url"),
            @Result(property = "resWebUper", column = "res_web_uper"),
            @Result(property = "resWebNum", column = "res_web_num"),
            @Result(property = "resWebPoster", column = "res_web_poster"),
    })
    List<ResWeb> getResourcesWeb(String resWebType);
}
