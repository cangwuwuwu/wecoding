package work.niter.wecoding.admin.access.controller;

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

/**
 * @author xiaozhai
 * @date 2020/4/15 16:33
 * @description
 */

@RestController
@RequestMapping("/admin/comp/access")
public class accessController {

    @Autowired
    private AccessService accessService;

    @GetMapping
    public ResponseEntity<Access> getAllAccessInfo(){
        return ResponseEntity.ok(accessService.findInfo());
    }

    //记录网站访问次数
    @AccessView
    @GetMapping("signIn")
    public void  accessSignIn(){}

    //记录校园指南页面访问次数
    @PageView("guide")
    @GetMapping("guide")
    public void  accessGuide(){}

    //记录帮助页面访问次数
    @PageView("help")
    @GetMapping("help")
    public void  accessHelp(){}

    //记录资源分享页面访问次数
    @PageView("res")
    @GetMapping("res")
    public void  accessRes(){}

    //记录课程页面访问次数
    @PageView("course")
    @GetMapping("course")
    public void  accessCourse(){}

    //记录电费提醒页面访问次数
    @PageView("electric")
    @GetMapping("electric")
    public void  accessElectric(){}

    //记录财务页面访问次数
    @PageView("finance")
    @GetMapping("spend")
    public void  accessSpend(){}




}
