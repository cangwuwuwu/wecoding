package work.niter.wecoding.res.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.res.entity.ResWebAudit;

import java.util.List;

/**
 * @author CangWu
 * @date 2019/11/24 12:19
 * @description
 *  未审核在线资源mapper
 */
public interface ResWebAuditMapper extends Mapper<ResWebAudit> {


    /*模糊查询在线资源信息*/
    @Select("select * from resource_web_audit where CONCAT( res_web_type,res_web_name,res_web_uper) LIKE CONCAT('%',#{search},'%')")
    @Results(id="web_result_map", value = {
            @Result(id = true, property = "Id", column = "id"),
            @Result(property = "resWebType", column = "res_web_type"),
            @Result(property = "resWebName", column = "res_web_name"),
            @Result(property = "resWebDescribe", column = "res_web_describe"),
            @Result(property = "resWebUrl", column = "res_web_url"),
            @Result(property = "resWebUper", column = "res_web_uper"),
            @Result(property = "resWebNum", column = "res_web_num"),
            @Result(property = "resWebPoster", column = "res_web_poster"),
            @Result(property = "resWebEmail", column = "res_web_email"),
    })
    List<ResWebAudit> searchResourceAuditWeb(String search);
}
