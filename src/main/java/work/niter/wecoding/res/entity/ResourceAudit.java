package work.niter.wecoding.res.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xiaozhai
 * @date 2019/11/24 12:10
 * @description
 *  未审核网盘实体类
 */
@Data
@Table(name = "resources_audit")
public class ResourceAudit {
    @Id
    private Integer resId;
    private Integer resForm;
    private String resType;
    private String resName;
    private String resDescribe;
    private String resUrl;
    private String resPassword;
    private Date resUpTime;
    private String resUploader;
    private String resUpEmail;
}
