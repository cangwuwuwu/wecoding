package work.niter.wecoding.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: Cangwu
 * @Date: 2019/10/6 11:40
 * @Description:
 */
@Data
@Table(name = "dormitory")
public class BuildRoom {
    private Integer id;
    private String name;
    private String value;
}
