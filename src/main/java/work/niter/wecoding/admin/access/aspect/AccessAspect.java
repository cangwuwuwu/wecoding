package work.niter.wecoding.admin.access.aspect;

import com.github.pagehelper.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import work.niter.wecoding.admin.access.annotation.PageView;
import work.niter.wecoding.admin.access.utils.IpUtils;
import work.niter.wecoding.admin.access.utils.RedisUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaozhai
 * @date 2020/4/11 12:34
 * @description 统计用户的切面类
 */
@Aspect
@Configuration
public class AccessAspect {

    @Autowired
    private RedisUtils redisUtils;

    /*切入点*/
    @Pointcut("@annotation(work.niter.wecoding.admin.access.annotation.AccessView)")
    public void accessView(){}

    @Pointcut("@annotation(work.niter.wecoding.admin.access.annotation.PageView)")
    public void pageView(){}

    //用户访问量
    @After("accessView()")
    public void doAccessViewAfter(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateS = format.format(date);
        try {
            String accessDayKey = "userAccess:day:" + dateS;
            String ipAddr = IpUtils.getIpAddr();
            String rIpAddr = ipAddr.replace(".", "");
            Long ipAddrL = Long.valueOf(rIpAddr);
            String accessUvDayKey = "uvAccess:day:"+ dateS;
            redisUtils.setUserAccess(accessDayKey);
            redisUtils.add(accessUvDayKey, ipAddrL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //各个页面访问量
    @After("pageView()")
    public void doPageViewAfter(JoinPoint jp){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateS = format.format(date);
        String pageName = getPageName(jp);
        try {
            String pageViewKey = pageName + ":day:" + dateS;
            redisUtils.setUserAccess(pageViewKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private String getPageName(JoinPoint jp) {
        PageView pageView = ((MethodSignature)jp.getSignature()).getMethod().getAnnotation(PageView.class);
        return pageView.value();
    }


}
