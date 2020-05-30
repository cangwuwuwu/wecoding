package work.niter.wecoding.admin.access.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.admin.entity.AccessPage;

/**
 * @Author xiaozhai
 * @Date 2020/4/19 16:18
 * @Description:
 */
public interface AccessPageMapper extends Mapper<AccessPage> {

    //获取离当前时间之前的最近的一天中页面访问数据
    @Select("select * from access_page where page_view_time < #{date} order by page_view_time desc limit 1;")
    @Results(id = "accessPage", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "guideDay", column = "guide_day"),
            @Result(property = "guideMonth", column = "guide_month"),
            @Result(property = "guideYear", column = "guide_year"),
            @Result(property = "resDay", column = "res_day"),
            @Result(property = "resMonth", column = "res_month"),
            @Result(property = "resYear", column = "res_year"),
            @Result(property = "courseDay", column = "course_day"),
            @Result(property = "courseMonth", column = "course_month"),
            @Result(property = "courseYear", column = "course_year"),
            @Result(property = "financeDay", column = "finance_day"),
            @Result(property = "financeMonth", column = "finance_month"),
            @Result(property = "financeYear", column = "finance_year"),
            @Result(property = "electricDay", column = "electric_day"),
            @Result(property = "electricMonth", column = "electric_month"),
            @Result(property = "electricYear", column = "electric_year"),
            @Result(property = "helpDay", column = "help_day"),
            @Result(property = "helpMonth", column = "help_month"),
            @Result(property = "helpYear", column = "help_year"),
            @Result(property = "pageViewTime", column = "page_view_time"),
    })
    AccessPage getAccessPageY(String date);
}
