package work.niter.wecoding.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * @Author: Cangwu
 * @Date: 2019/9/20 22:39
 * @Description:
 */
@Data
public class CompSpend {
    @Id
    private Integer id;
    private String type;
    private String time;
    private String describe;
    private String small;
    private String big;
    private String name;
    private String email;
    private String others;
}
