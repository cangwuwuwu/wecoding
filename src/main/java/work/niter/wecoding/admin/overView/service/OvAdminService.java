package work.niter.wecoding.admin.overView.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.niter.wecoding.user.mapper.CompMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiaozhai
 * @Date 2019/11/27 10:59
 * @Description:
 */

@Service
public class OvAdminService {

    @Autowired
    private CompMapper studentMapper;

    /**
     * 后台管理管理-协会概览管理 -- 查询学生学院分布情况
     */
    public Map<String, Integer> findDeptDistribution() {
        List<String> deptInfo = studentMapper.findDeptInfo();
        Map<String, Integer> map = new HashMap<>();
        deptInfo.forEach(item -> {
            String i = null;
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
}
