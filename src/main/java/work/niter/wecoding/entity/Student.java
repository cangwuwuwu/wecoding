package work.niter.wecoding.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:20
 * @Description:
 */
@Data
@Table(name = "tb_stu")
public class Student {
    @Id
    private String stuId;
    private String stuName;
    private String stuUsername;
    private String stuPhone;
    private String stuGender;
    private String stuEmail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stuBirthday;
    private String stuArea;
    private Date stuRegistTime;
    private Date stuModifyTime;
    private String stuInfo;
    private String stuImg;
    private String stuBigImg;
}
