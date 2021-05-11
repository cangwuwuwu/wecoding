package work.niter.wecoding.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author CangWu
 * @date 2020/4/19 10:35
 * @description 各个页面访问量统计
 */
@Table(name = "access_page")
@Data
public class AccessPage {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Long guideDay; //校园指南每日访问量
    private Long guideMonth; //校园指南每月访问量
    private Long guideYear; //校园指南每年访问量
    private Long resDay; //资源分享每日访问量
    private Long resMonth; //资源分享每月访问量
    private Long resYear; //资源分享每年访问量
    private Long courseDay; //计协课程每日访问量
    private Long courseMonth; //计协课程每月访问量
    private Long courseYear; //计协课程每年访问量
    private Long financeDay; //财务公示每日访问量
    private Long financeMonth; //财务公示每月访问量
    private Long financeYear; //财务公示每年访问量
    private Long electricDay; //电量通知每日访问量
    private Long electricMonth; //电量通知每月访问量
    private Long electricYear; //电量通知每年访问量
    private Long helpDay; //帮助每日访问量
    private Long helpMonth; //帮助每月访问量
    private Long helpYear; //帮助页面每年访问量
    private Date pageViewTime; //日期
}
