package work.niter.wecoding.res.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author xiaozhai
 * @Date 2019/11/24 12:08
 * @Description:
 * 未审核在线资源实体类
 */

@Data
@Table(name = "resource_web_audit")
public class ResWebAudit {

    @javax.persistence.Id
    private Integer Id;
    private String resWebType;
    private String resWebName;
    private String resWebDescribe;
    private String resWebUrl;
    private String resWebUper;
    private Integer resWebNum;
    private String resWebPoster;

}
