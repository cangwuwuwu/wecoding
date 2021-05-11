package work.niter.wecoding.course.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/10/21 16:54
 * @description
 */
@Data
@Table(name = "apply")
public class Apply {
    @Id
    private Integer id;
    private String courseId;
    private String stuId;
    private String name;
    private String head;
    private Date applyTime;
}
