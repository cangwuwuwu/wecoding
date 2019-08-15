package work.niter.wecoding.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cangwu
 * @Date: 2019/8/6 17:38
 * @Description: 资源信息
 */
@Data
public class Resource {
    private Integer resId;
    private Integer resForm;
    private String resType;
    private String resName;
    private String resDescribe;
    private String resUrl;
    private String resPassword;
    private Date resUpTime;
    private Date resFailTime;
    private ResMore resMore;
    private String resUploader;
}
