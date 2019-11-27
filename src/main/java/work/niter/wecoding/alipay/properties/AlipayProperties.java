package work.niter.wecoding.alipay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Cangwu
 * @Date: 2019/10/27 14:50
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayProperties {
    private String gatewayUrl;
    private String appid;
    private String pid;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String signType = "RSA2";
    private String formate = "json";
    private String charset = "UTF-8";
    private String notifyUrl;
    private String payNum;
    private String subject;
}
