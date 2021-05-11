package work.niter.wecoding.electric.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author Cangwu
 * @date 2019/10/6 11:40
 * @description
 */
@Data
@Table(name = "dormitory")
public class BuildRoom {
    private Integer id;
    private String name;
    private String value;
}
