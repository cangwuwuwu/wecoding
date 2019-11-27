package work.niter.wecoding.electric.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: Cangwu
 * @Date: 2019/10/6 11:40
 * @Description:
 */
@Data
@Table(name = "electric_account")
public class EleAccount {
    private String stuId;
    private String stuEmail;
    private String stuRoom;
    private String stuBuild;
}
