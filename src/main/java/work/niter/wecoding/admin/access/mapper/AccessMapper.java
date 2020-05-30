package work.niter.wecoding.admin.access.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.admin.entity.Access;
import work.niter.wecoding.admin.entity.AccessPage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * @Author xiaozhai
 * @Date 2020/4/11 19:55
 * @Description:
 */

public interface AccessMapper extends Mapper<Access> {

    //获取离当前时间之前的最近的一天中access数据
    @Select("select * from access where access_time < #{date} order by access_time desc limit 1;")
    @Results(id = "accessResult", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "accessDay", column = "access_day"),
            @Result(property = "accessMonth", column = "access_month"),
            @Result(property = "accessYear", column = "access_year"),
            @Result(property = "accessAll", column = "access_all"),
            @Result(property = "accessUvDay", column = "access_uv_day"),
            @Result(property = "accessUvMonth", column = "access_uv_month"),
            @Result(property = "accessTime", column = "access_time"),
    })
    Access getAccessY(String date);


    //获取今天注册用户的人数
    @Select("select count(*) from comp_stu where stu_regist_time >= #{date};")
    Integer getNewUserT(String date);

    //获取这个月的注册人数
    @Select("select count(*) from comp_stu where stu_regist_time >= #{monthStart} and stu_regist_time < #{tomorrow};")
    Integer getNewUserM(String monthStart, String tomorrow);

    //获取总用户数
    @Select("select count(*) from comp_stu")
    Integer getNewUserA();

    //获取某个月的最后一条记录
    @Select("select access_month from access where access_time >= #{start} and access_time <= #{end} order by access_time desc limit 1;")
    Integer getMonthLastValue(String start, String end);

    //获取截止当前日期的访问趋势
    @Select("select access_month from access where FIND_IN_SET(access_time, #{date}) order by access_time desc")
    List<Long> getAccessTrend(String date);
}
