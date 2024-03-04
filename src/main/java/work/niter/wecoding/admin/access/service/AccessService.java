package work.niter.wecoding.admin.access.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import work.niter.wecoding.admin.access.mapper.AccessMapper;
import work.niter.wecoding.admin.access.utils.AccessUtils;
import work.niter.wecoding.admin.access.utils.RedisUtils;
import work.niter.wecoding.admin.entity.Access;
import work.niter.wecoding.admin.entity.AccessPage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author xiaozhai
 * @date 2020/4/15 16:36
 * @description
 */

@Service
public class AccessService {


    @Autowired
    private AccessMapper accessMapper;

    @Autowired
    private AccessUtils accessUtils;

    public Access findInfo() {
        LocalDate nowTime = LocalDate.now();
        //获取完整的访问量数据和用户活跃度数据
        Access accessData = accessUtils.getAccessData();
        //获取注册用户相关的数据
        Integer newUserT = accessMapper.getNewUserT(nowTime.toString());
        Integer newUserM = accessMapper.getNewUserM(nowTime.with(TemporalAdjusters.firstDayOfMonth()).toString(), nowTime.plusDays(1L).toString());
        Integer newUserA = accessMapper.getNewUserA();
        accessData.setNewUserDay(newUserT);
        accessData.setNewUserMonth(newUserM);
        accessData.setNewUserAll(newUserA);
        //获取用户访问量趋势
        List<Long> accessTrend = new ArrayList<Long>();
        int currentMonth = nowTime.getMonthValue();
        //如果本月是一月份则只返回这个月的访问量
        if (currentMonth == 1) {
            accessTrend.add(accessData.getAccessMonth());
        } else {
            //把这一年之前每个月的最后一天的日期当参数传入到mapper中查询结果
            for (int month = 1; month < currentMonth; month++) {
                LocalDate localDate = nowTime.minusMonths(month);
                LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
                LocalDate startDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
                Integer value = accessMapper.getMonthLastValue(startDay.toString(), lastDay.toString());
                if (value == null) {
                    value = 0;
                }
                accessTrend.add(value.longValue());
            }

            Collections.reverse(accessTrend);
            accessTrend.add(accessData.getAccessMonth());
            //把当月之后的月份访问量设为0
            if (accessTrend.size() < 12) {
                for (int i = accessTrend.size() + 1; i <= 12; i++) {
                    accessTrend.add(0L);
                }
            }
        }
        accessData.setAccessTrend(accessTrend);

        //获取各个页面访问量
        AccessPage pageData = accessUtils.getPageViewData();
        return getPageViewData(accessData, pageData);
    }

    private Access getPageViewData(Access accessData, AccessPage pageData) {
        List<Map<String, Object>> pageDayList = new ArrayList<>();
        List<Map<String, Object>> pageMonthList = new ArrayList<>();
        List<Map<String, Object>> pageYearList = new ArrayList<>();
        String guideName = "校园指南";
        String resName = "资源分享";
        String courseName = "计协课程";
        String financeName = "财务公示";
        String electricName = "电量通知";
        String HelpName = "帮助";
        //校园指南
        Map<String, Object> guideMap1 = new HashMap<>();
        Map<String, Object> guideMap2 = new HashMap<>();
        Map<String, Object> guideMap3 = new HashMap<>();
        guideMap1.put("name", guideName);
        guideMap1.put("value", pageData.getGuideDay());
        pageDayList.add(guideMap1);
        guideMap2.put("name", guideName);
        guideMap2.put("value", pageData.getGuideMonth());
        pageMonthList.add(guideMap2);
        guideMap3.put("name", guideName);
        guideMap3.put("value", pageData.getGuideYear());
        pageYearList.add(guideMap3);
        //资源分享
        Map<String, Object> resMap1 = new HashMap<>();
        Map<String, Object> resMap2 = new HashMap<>();
        Map<String, Object> resMap3 = new HashMap<>();
        resMap1.put("name", resName);
        resMap1.put("value", pageData.getResDay());
        pageDayList.add(resMap1);
        resMap2.put("name", resName);
        resMap2.put("value", pageData.getResMonth());
        pageMonthList.add(resMap2);
        resMap3.put("name", resName);
        resMap3.put("value", pageData.getResYear());
        pageYearList.add(resMap3);
        //计协课程
        Map<String, Object> courseMap1 = new HashMap<>();
        Map<String, Object> courseMap2 = new HashMap<>();
        Map<String, Object> courseMap3 = new HashMap<>();
        courseMap1.put("name", courseName);
        courseMap1.put("value", pageData.getCourseDay());
        pageDayList.add(courseMap1);
        courseMap2.put("name", courseName);
        courseMap2.put("value", pageData.getCourseMonth());
        pageMonthList.add(courseMap2);
        courseMap3.put("name", courseName);
        courseMap3.put("value", pageData.getCourseYear());
        pageYearList.add(courseMap3);
        //财务公示
        Map<String, Object> financeMap1 = new HashMap<>();
        Map<String, Object> financeMap2 = new HashMap<>();
        Map<String, Object> financeMap3 = new HashMap<>();
        financeMap1.put("name", financeName);
        financeMap1.put("value", pageData.getFinanceDay());
        pageDayList.add(financeMap1);
        financeMap2.put("name", financeName);
        financeMap2.put("value", pageData.getFinanceMonth());
        pageMonthList.add(financeMap2);
        financeMap3.put("name", financeName);
        financeMap3.put("value", pageData.getFinanceYear());
        pageYearList.add(financeMap3);
        //电量通知
        Map<String, Object> electricMap1 = new HashMap<>();
        Map<String, Object> electricMap2 = new HashMap<>();
        Map<String, Object> electricMap3 = new HashMap<>();
        electricMap1.put("name", electricName);
        electricMap1.put("value", pageData.getElectricDay());
        pageDayList.add(electricMap1);
        electricMap2.put("name", electricName);
        electricMap2.put("value", pageData.getElectricMonth());
        pageMonthList.add(electricMap2);
        electricMap3.put("name", electricName);
        electricMap3.put("value", pageData.getElectricYear());
        //帮助
        Map<String, Object> helpMap1 = new HashMap<>();
        Map<String, Object> helpMap2 = new HashMap<>();
        Map<String, Object> helpMap3 = new HashMap<>();
        helpMap1.put("name", HelpName);
        helpMap1.put("value", pageData.getHelpDay());
        pageDayList.add(helpMap1);
        helpMap2.put("name", HelpName);
        helpMap2.put("value", pageData.getHelpMonth());
        pageMonthList.add(helpMap2);
        helpMap3.put("name", HelpName);
        helpMap3.put("value", pageData.getHelpYear());
        pageYearList.add(helpMap3);
        accessData.setPageViewDay(pageDayList);
        accessData.setPageViewMonth(pageMonthList);
        accessData.setPageViewYear(pageYearList);

        return accessData;
    }
}
