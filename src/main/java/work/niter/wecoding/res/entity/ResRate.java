package work.niter.wecoding.res.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/11/9 10:47
 * @description 资源评分实体类
 */
@Data
@Table(name = "resource_rate")
public class ResRate {
    private Integer id;
    private Integer resId;
    private Integer stuId;
    private Double rate;
    private Date updateTime;
}
