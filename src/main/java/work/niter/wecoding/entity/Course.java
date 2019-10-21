package work.niter.wecoding.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: Cangwu
 * @Date: 2019/10/11 16:47
 * @Description: 课程实体类
 */
@Data
@Table(name = "course")
public class Course {
    private String id;
    private Integer type;
    private Integer apply;
    private String star;
    private String position;
    private String time;
    private String poster;
    private String title;
    private String host;
    private String avatar;
    private String content;
}
