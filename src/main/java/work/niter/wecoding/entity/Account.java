package work.niter.wecoding.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:24
 * @Description:
 */
@Data
@Table(name = "account")
public class Account {
    @Id
    private String stuUsername;
    private String stuPassword;
}
