package work.niter.wecoding.admin.access.synchro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.niter.wecoding.admin.access.mapper.AccessMapper;
import work.niter.wecoding.admin.access.mapper.AccessPageMapper;
import work.niter.wecoding.admin.access.utils.AccessUtils;
import work.niter.wecoding.admin.access.utils.RedisUtils;
import work.niter.wecoding.admin.entity.Access;
import work.niter.wecoding.admin.entity.AccessPage;


import javax.annotation.Resource;
import javax.script.ScriptException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author xiaozhai
 * @date 2020/4/11 19:09
 * @description
 */
@Component
public class SynchronizationTask {

    @Autowired
    private AccessUtils accessUtils;

    @Autowired
    private AccessMapper accessMapper;

    @Autowired
    private AccessPageMapper accessPageMapper;

    @Autowired
    private RedisUtils redisUtils;

    /*每天十二点将访问量同步到数据库中*/
    @Scheduled(cron = "0 50 23 * * ? ")
    public void task1() throws ScriptException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateS = format.format(date);
        String accessDayKey = "userAccess:day:" + dateS;
        String accessUvDayKey = "uvAccess:day:" + dateS;
        String guideKey = "guide:day:" + dateS;
        String resKey = "res:day:" + dateS;
        String financeKey = "finance:day:" + dateS;
        String electricKey = "electric:day:" + dateS;
        String courseKey = "course:day:" + dateS;
        String helpKey = "help:day:" + dateS;
        //将redis中的数据和数据库中的数据相加获取完整的访问量数据和用户活跃度数据以及各个页面访问量数据
        Access accessData = accessUtils.getAccessData();
        AccessPage pageViewData = accessUtils.getPageViewData();
        accessPageMapper.insertSelective(pageViewData);
        accessMapper.insertSelective(accessData);
        redisUtils.delUserAccess(accessDayKey, accessUvDayKey, guideKey, resKey, financeKey, electricKey, courseKey, helpKey);
    }


}
