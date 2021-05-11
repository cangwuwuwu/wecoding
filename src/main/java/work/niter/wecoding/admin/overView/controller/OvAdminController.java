package work.niter.wecoding.admin.overView.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.admin.access.annotation.AccessView;
import work.niter.wecoding.admin.access.annotation.PageView;
import work.niter.wecoding.admin.access.service.AccessService;
import work.niter.wecoding.admin.entity.Access;
import work.niter.wecoding.admin.overView.service.OvAdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhai
 * @date 2019/11/27 10:56
 * @description
 */
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/comp/ov")
@RestController
public class OvAdminController {

    @Autowired
    private OvAdminService adminService;

    @Autowired
    private AccessService accessService;

    /**
     * 后台管理管理-协会概览管理 -- 查询学生学院分布情况
     */
    @GetMapping("/getDeptDistribution")
    public ResponseEntity<Map<String, Integer>> getDeptDistribution(){
        Map<String, Integer> infos = adminService.findDeptDistribution();
        return ResponseEntity.ok(infos);
    }

    /**
     * 后台管理管理-协会概览管理 -- 查询学生男女分布情况
     */
    @GetMapping("/getGenderDis")
    public ResponseEntity<Map<String, Integer>> getGenderDis(){
        Map<String, Integer> map = adminService.findGenderDis();
        return ResponseEntity.ok(map);
    }

    /**
     * 后台管理管理-协会概览管理 -- 查询学生资源分布和年级分布情况
     */
    @GetMapping("/getResAndGrade")
    public ResponseEntity<List<Map<String, Integer>>> getResAndGrade(){
        List<Map<String, Integer>> list = adminService.getResAndGrade();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getOVInfo")
    public ResponseEntity<Map<String ,Object>> getOVData(){
        //查询学生学院分布情况
        Map<String, Integer> deptInfos = adminService.findDeptDistribution();
        //查询学生男女分布情况
        Map<String, Integer> genderInfo = adminService.findGenderDis();
        //查询学生资源分布和年级分布情况
        List<Map<String, Integer>> ResInfo = adminService.getResAndGrade();
        //查询网站访问情况
        Access accessInfo = accessService.findInfo();
        Map<String,Object> map = new HashMap<>();
        map.put("dept", deptInfos);
        map.put("gender", genderInfo);
        map.put("res", ResInfo);
        map.put("access", accessInfo);
        return ResponseEntity.ok(map);

    }



}
