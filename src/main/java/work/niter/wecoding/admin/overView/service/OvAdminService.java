package work.niter.wecoding.admin.overView.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.niter.wecoding.res.mapper.ResWebMapper;
import work.niter.wecoding.res.mapper.ResourceMapper;
import work.niter.wecoding.user.mapper.AccountMapper;
import work.niter.wecoding.user.mapper.CompMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhai
 * @date 2019/11/27 10:59
 * @description
 */

@Service
public class OvAdminService {

    @Autowired
    private CompMapper studentMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResWebMapper resWebMapper;

    @Autowired
    private AccountMapper accountMapper;


    /**
     * 后台管理管理-协会概览管理 -- 查询学生学院分布情况
     */
    public Map<String, Integer> findDeptDistribution() {
        List<String> deptInfo = studentMapper.findDeptInfo();
        Map<String, Integer> map = new HashMap<>();
        deptInfo.forEach(item -> {
            String i;
            try {
                i = item.substring(0, item.indexOf("/"));
            } catch (Exception e) {
                i = item;
            }
            Integer count = map.get(i);
            map.put(i, (count == null) ? 1 : count + 1);
        });
        return map;
    }

    /**
     * 后台管理管理-协会概览管理 -- 查询学生性别分布情况
     */
    public Map<String, Integer> findGenderDis() {
        Map<String, Integer> map = new HashMap<>();
        Integer boys = studentMapper.findMaleCount();
        Integer all = studentMapper.findStudentCount();
        Integer girls = all - boys;
        map.put("boys", boys);
        map.put("girls", girls);
        map.put("all", all);
        return map;
    }

    /**
     * 后台管理管理-协会概览管理 -- 查询资源和年级分布情况
     */
    public List<Map<String, Integer>> getResAndGrade() {
        List<Map<String, Integer>> list = new ArrayList<>();
        Map<String, Integer> resMap = new HashMap<>();
        Map<String, Integer> gradeMap = new HashMap<>();

        //查询资源分布情况
        List<String> resType = resourceMapper.searchResType();
        List<String> resWebType = resWebMapper.searchResWebType();
        resType.addAll(resWebType);
        resType.forEach(item -> {
            try {
                String[] split = item.split(",");
                for (String s : split) {
                    String s2 = s.trim();
                    Integer count = resMap.get(s2);
                    resMap.put(s2, (count == null) ? 1 : count + 1);
                }
            } catch (Exception e) {
                String s3 = item.trim();
                Integer count = resMap.get(s3);
                resMap.put(s3, (count == null) ? 1 : count + 1);
            }
        });

        //查询年级分布情况
        List<String> stuIds = accountMapper.searchStuId();
        stuIds.forEach(item -> {
            item = item.substring(2, 4);
            Integer count = gradeMap.get(item);
            gradeMap.put(item, (count == null) ? 1 : count + 1);
        });

        //将年级和资源map 添加到集合中
        list.add(resMap);
        list.add(gradeMap);

        return list;
    }
}
