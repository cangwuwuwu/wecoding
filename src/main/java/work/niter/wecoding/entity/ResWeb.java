package work.niter.wecoding.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Cangwu
 * @Date: 2019/8/18 15:25
 * @Description: 在线资源实体类
 */
@Data
@Table(name = "resource_web")
public class ResWeb {
    @Id
    private Integer id;
    private String resWebType;
    private String resWebName;
    private String resWebDescribe;
    private String resWebUrl;
    private String resWebUper;
    private Integer resWebNum;
    private String resWebPoster;
}
