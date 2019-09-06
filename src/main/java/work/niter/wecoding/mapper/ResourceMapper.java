package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.entity.ResMore;
import work.niter.wecoding.entity.Resource;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/7 1:14
 * @Description:
 */
public interface ResourceMapper extends Mapper<Resource> {

    @Select("select * from resources where instr(res_type,#{resType})")
    @Results(id="res_result_map", value = {
            @Result(id = true, property = "resId", column = "res_id"),
            @Result(property = "resForm", column = "res_form"),
            @Result(property = "resType", column = "res_type"),
            @Result(property = "resName", column = "res_name"),
            @Result(property = "resDescribe", column = "res_describe"),
            @Result(property = "resUrl", column = "res_url"),
            @Result(property = "resPassword", column = "res_password"),
            @Result(property = "resUpTime", column = "res_up_time"),
            @Result(property = "resMore", column = "res_id",
            one = @One(select = "work.niter.wecoding.mapper.ResMoreMapper.selectByPrimaryKey")),
            @Result(property = "resUploader", column = "res_uploader")

    })
    List<Resource> getResources(String resType);

    @Select("select * from resources")
    @ResultMap("res_result_map")
    List<Resource> getAllResourceService();

    @Select("select * from resources where instr(res_name,#{resName})")
    @ResultMap("res_result_map")
    List<Resource> getSearchResByNameService(String resName);

    @Insert("insert into resource_more values(last_insert_id(),#{resStatus},#{resPoint},#{resHeat}")
    int insertResMore(ResMore resMore);
}
