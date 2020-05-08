package work.niter.wecoding.admin.access.mapper;

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
    AccessPage getAccessPageY(String date);
}
