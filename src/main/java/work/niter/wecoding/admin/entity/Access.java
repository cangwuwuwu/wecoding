package work.niter.wecoding.admin.entity;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author xiaozhai
 * @Date 2020/4/11 12:18
 * @Description: 访问量实体类
 */
@Table(name = "access")
@Data
public class Access {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id; //主键
    private Long accessDay; //当日访问量
    private Long accessMonth; //当月访问量
    private Long accessYear; //当年访问量
    private Long accessAll; //总访问量
    private Long accessUvDay; //日活
    private Long accessUvMonth; //月活
    private Date accessTime; // 访问日期

    @Transient
    private List<Long> accessTrend; //访问趋势
    @Transient
    private Integer newUserDay; //每日新增用户
    @Transient
    private Integer newUserMonth; //每月新增用户
    @Transient
    private Integer newUserAll; //总用户
    @Transient
    private List<Map<String, Object>> pageViewDay;
    @Transient
    private List<Map<String, Object>> pageViewMonth;
    @Transient
    private List<Map<String, Object>> pageViewYear;

}
