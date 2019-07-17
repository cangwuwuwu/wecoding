package work.niter.wecoding.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:20
 * @Description:
 */
@Data
public class Student {
    private Integer stuId;
    private String stuName;
    private String stuUsername;
    private String stuPhone;
    private String stuEmail;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date stuBirthday;
    private String stuProvince;
    private String stuCity;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date stuRegistTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date stuModifyTime;
    private String stuInfo;
}
