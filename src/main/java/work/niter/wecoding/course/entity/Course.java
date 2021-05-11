package work.niter.wecoding.course.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author Cangwu
 * @date 2019/10/11 16:47
 * @description 课程实体类
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
