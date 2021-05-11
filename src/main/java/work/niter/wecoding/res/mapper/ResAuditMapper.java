package work.niter.wecoding.res.mapper;

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.res.entity.ResourceAudit;

import java.util.List;

/**
 * @author CangWu
 * @date 2019/11/24 12:17
 * @description
 *  未审核网盘资源mapper
 */
public interface ResAuditMapper extends Mapper<ResourceAudit> {

    /*模糊查询未审核资源网盘信息*/
    @Select("select * from resources_audit where CONCAT( res_name,res_uploader,res_type,res_describe,res_up_time) LIKE CONCAT('%',#{search},'%')")
    @Results(id="res_result_map", value = {
            @Result(id = true, property = "resId", column = "res_id"),
            @Result(property = "resForm", column = "res_form"),
            @Result(property = "resType", column = "res_type"),
            @Result(property = "resName", column = "res_name"),
            @Result(property = "resDescribe", column = "res_describe"),
            @Result(property = "resUrl", column = "res_url"),
            @Result(property = "resPassword", column = "res_password"),
            @Result(property = "resUpTime", column = "res_up_time"),
            @Result(property = "resUploader", column = "res_uploader"),
            @Result(property = "resUpEmail", column = "res_up_email"),

    })
    List<ResourceAudit> searchResourceAudit(String search);
}
