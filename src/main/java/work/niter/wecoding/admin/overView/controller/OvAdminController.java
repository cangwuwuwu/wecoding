package work.niter.wecoding.admin.overView.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.admin.overView.service.OvAdminService;

import java.util.List;
import java.util.Map;

/**
 * @Author xiaozhai
 * @Date 2019/11/27 10:56
 * @Description:
 */
@RequestMapping("/admin/comp/ov")
@RestController
public class OvAdminController {

    @Autowired
    private OvAdminService adminService;

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

}
