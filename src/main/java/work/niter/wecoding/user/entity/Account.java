package work.niter.wecoding.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:24
 * @Description:
 */
@Data
@Table(name = "account")
public class Account {
    @Id
    private String stuId;
    private String stuPassword;
    private Integer stuAuth;

    @Transient
    private String stuName;
    @Transient
    private String stuPhone;
    @Transient
    private String stuAuthString;
    @Transient
    private String stuDesc;


}
