package work.niter.wecoding.res.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/8/6 17:38
 * @description 资源信息
 */
@Data
@Table(name = "resources")
public class Resource {
    @Id
    private Integer resId;
    private Integer resForm;
    private String resType;
    private String resName;
    private String resDescribe;
    private String resUrl;
    private String resPassword;
    private Date resUpTime;
    private ResMore resMore;
    private String resUploader;
}
