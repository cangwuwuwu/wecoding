package work.niter.wecoding.spend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/9/20 22:39
 * @description
 */
@Data
@Table(name = "comp_spend")
public class CompSpend {
    @Id
    private Integer id;
    @Column(name = "`number`")
    private Integer number;
    private String type;
    private Date time;
    @Column(name = "`desc`")
    private String desc;
    private String small;
    private String big;
    private String name;
    private String email;
    private String others;

}
