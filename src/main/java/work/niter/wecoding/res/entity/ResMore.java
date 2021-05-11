package work.niter.wecoding.res.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Cangwu
 * @date 2019/8/14 13:46
 * @description
 */
@Data
@Table(name = "resource_more")
public class ResMore {
    @Id
    private Integer resId;
    private Integer resStatus;
    private Double resPoint;
    private Integer resHeat;
}
