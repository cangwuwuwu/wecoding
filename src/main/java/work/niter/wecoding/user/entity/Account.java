package work.niter.wecoding.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * @author Cangwu
 * @date 2019/7/14 1:24
 * @description
 */
@Data
@Table(name = "account")
public class Account {
    @Id
    private String stuId;

    @JsonIgnore
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
