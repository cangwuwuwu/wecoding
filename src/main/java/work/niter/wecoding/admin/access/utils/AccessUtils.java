package work.niter.wecoding.admin.access.utils;

import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.niter.wecoding.admin.access.mapper.AccessMapper;
import work.niter.wecoding.admin.access.mapper.AccessPageMapper;
import work.niter.wecoding.admin.entity.Access;
import work.niter.wecoding.admin.entity.AccessPage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author xiaozhai
 * @Date 2020/4/17 15:20
 * @Description:
 */
@Component
public class AccessUtils {

    @Autowired
    private AccessMapper accessMapper;

    @Autowired
    private AccessPageMapper accessPageMapper;

    @Autowired
    private RedisUtils redisUtils;

    //获取完整的用户访问量数据和用户活跃度信息的数据
    public  Access getAccessData(){
        LocalDate nowTime = LocalDate.now();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateS = format.format(date);
        Access accessT = new Access();
        //从redis中获取当日访问量和日活
        String accessDayKey = "userAccess:day:" + dateS;
        String accessUvDayKey = "uvAccess:day:" + dateS;
        Long dayValue = redisUtils.getUserAccess(accessDayKey);
        Long uvDayValue = redisUtils.size(accessUvDayKey);
        accessT.setAccessDay(dayValue);
        accessT.setAccessUvDay(uvDayValue);
        accessT.setAccessTime(date);
        //从数据库中获取离当前时间之前的最近的一天中access最终的数据
        Access accessY = accessMapper.getAccessY(dateS); //获取昨天输入的数据
        System.out.println(accessY);
        //如果数据库没有之前的数据，则把今天的数据当成最终的数据
        if (accessY == null){
            accessT = initAccess(accessT);
        }else {
            accessT.setAccessMonth(dayValue + accessY.getAccessMonth());
            accessT.setAccessYear(dayValue + accessY.getAccessYear());
            accessT.setAccessAll(dayValue + accessY.getAccessAll());
            accessT.setAccessUvMonth(uvDayValue + accessY.getAccessUvMonth());

//            //判断今天是否是每个月第一天、每一年第一天
//            if (nowTime.getDayOfMonth() == 1){
//                accessT.setAccessMonth(dayValue);
//                accessT.setAccessUvMonth(uvDayValue);
//                if (nowTime.getDayOfYear() == 1){
//                    accessT.setAccessYear(dayValue);
//                }
//            }

            //判断今天是否与最近一天不在同一个月，或者同一年
            Date lasAccessTime = accessY.getAccessTime();
            LocalDate lasAccess = lasAccessTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (nowTime.getMonthValue() != lasAccess.getMonthValue()){
                accessT.setAccessMonth(dayValue);
                accessT.setAccessUvMonth(uvDayValue);
                if (nowTime.getYear() != lasAccess.getYear()){
                    accessT.setAccessYear(dayValue);
                }
            }
        }
        return accessT;
    }

    //获取各个页面完整的页面访问量
    public AccessPage getPageViewData(){
        LocalDate nowTime = LocalDate.now();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateS = format.format(date);
        AccessPage accessPage = new AccessPage();
        //从redis中获取当日各个页面的访问量
        Long guideDayValue = redisUtils.getUserAccess("guide:day:" + dateS);
        Long resDayValue = redisUtils.getUserAccess("res:day:" + dateS);
        Long financeDayValue = redisUtils.getUserAccess("finance:day:" + dateS);
        Long electricDayValue = redisUtils.getUserAccess("electric:day:" + dateS);
        Long courseDayValue = redisUtils.getUserAccess("course:day:" + dateS);
        Long helpDayValue = redisUtils.getUserAccess("help:day:" + dateS);
        accessPage.setCourseDay(courseDayValue);
        accessPage.setElectricDay(electricDayValue);
        accessPage.setFinanceDay(financeDayValue);
        accessPage.setGuideDay(guideDayValue);
        accessPage.setHelpDay(helpDayValue);
        accessPage.setResDay(resDayValue);
        accessPage.setPageViewTime(date);
        //从数据库中获取离当前时间之前的最近的一天中页面访问最终的数据
        AccessPage accessPageY = accessPageMapper.getAccessPageY(dateS);
        System.out.println(accessPageY);
        //如果数据库没有之前的数据，则把今天的数据当成最终的数据
        if (accessPageY == null){
            accessPageY = initAccessPage(accessPage);
        }else {
            accessPage.setGuideMonth(guideDayValue + accessPageY.getGuideMonth());
            accessPage.setGuideYear(guideDayValue + accessPageY.getGuideYear());
            accessPage.setResMonth(resDayValue + accessPageY.getResMonth());
            accessPage.setResYear(resDayValue + accessPageY.getResYear());
            accessPage.setFinanceMonth(financeDayValue + accessPageY.getFinanceMonth());
            accessPage.setFinanceYear(financeDayValue + accessPageY.getFinanceYear());
            accessPage.setElectricMonth(electricDayValue + accessPageY.getElectricMonth());
            accessPage.setElectricYear(electricDayValue + accessPageY.getElectricYear());
            accessPage.setCourseMonth(courseDayValue + accessPageY.getCourseMonth());
            accessPage.setCourseYear(courseDayValue + accessPageY.getCourseYear());
            accessPage.setHelpMonth(helpDayValue + accessPageY.getHelpMonth());
            accessPage.setHelpYear(helpDayValue + accessPageY.getHelpYear());

            //判断今天是否与最近一天不在同一个月，或者同一年
            Date lastViewTime = accessPageY.getPageViewTime();
            LocalDate lastView = lastViewTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (nowTime.getMonthValue() != lastView.getMonthValue()){
                accessPage.setGuideMonth(guideDayValue);
                accessPage.setResMonth(resDayValue);
                accessPage.setFinanceMonth(financeDayValue);
                accessPage.setElectricMonth(electricDayValue);
                accessPage.setCourseMonth(courseDayValue);
                accessPage.setHelpMonth(helpDayValue);
                if (nowTime.getYear()  != lastView.getMonthValue()){
                    accessPage.setGuideYear(guideDayValue);
                    accessPage.setResYear(resDayValue);
                    accessPage.setFinanceYear(financeDayValue);
                    accessPage.setElectricYear(electricDayValue);
                    accessPage.setCourseYear(courseDayValue);
                    accessPage.setHelpYear(helpDayValue);
                }
            }
        }
        return accessPage;
    }

    private AccessPage initAccessPage(AccessPage accessPage) {
        Long guideDayValue = accessPage.getGuideDay();
        Long resDayValue =accessPage.getResDay();
        Long financeDayValue = accessPage.getFinanceDay();
        Long electricDayValue = accessPage.getElectricDay();
        Long courseDayValue = accessPage.getCourseDay();
        Long helpDayValue = accessPage.getHelpDay();
        accessPage.setGuideMonth(guideDayValue);
        accessPage.setGuideYear(guideDayValue);
        accessPage.setResMonth(resDayValue);
        accessPage.setResYear(resDayValue);
        accessPage.setFinanceMonth(financeDayValue);
        accessPage.setFinanceYear(financeDayValue);
        accessPage.setElectricMonth(electricDayValue);
        accessPage.setElectricYear(electricDayValue);
        accessPage.setCourseMonth(courseDayValue);
        accessPage.setCourseYear(courseDayValue);
        accessPage.setHelpMonth(helpDayValue);
        accessPage.setHelpYear(helpDayValue);
        return accessPage;
    }

    //给Access赋初始值
    private  Access initAccess(Access access){
        Long dayValue = access.getAccessDay();
        access.setAccessMonth(dayValue);
        access.setAccessYear(dayValue);
        access.setAccessAll(dayValue);
        access.setAccessUvMonth(access.getAccessUvDay());
        return access;
    }
}
