package work.niter.wecoding.admin.resource.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.admin.access.annotation.PageView;
import work.niter.wecoding.admin.resource.service.ResAdminService;
import work.niter.wecoding.res.entity.ResWeb;
import work.niter.wecoding.res.entity.ResWebAudit;
import work.niter.wecoding.res.entity.Resource;
import work.niter.wecoding.res.entity.ResourceAudit;

/**
 * @Author xiaozhai
 * @Date 2019/11/27 10:50
 * @Description:
 */
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/comp/res")
@RestController
public class ResAdminController {

    @Autowired
    private ResAdminService adminService;

    /**
     * 后台管理管理-资源管理 --查询所有网盘资源信息
     */
    @GetMapping("/getResourceInfo")
    public ResponseEntity<PageInfo<Resource>> getResource(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                          @RequestParam(value = "search", required = false) String search){
        PageInfo<Resource> resourceInfo = adminService.findResourceInfo(page, size, search);
        return ResponseEntity.ok(resourceInfo);
    }

    /**
     * 后台管理管理-资源管理 --增加或修改资源信息
     */
    @PostMapping("/updateOrInsertResource")
    public ResponseEntity<Void> updateOrInsertResource(@RequestBody  Resource resource){
        adminService.updateOrInsertResource(resource);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --根据Id删除资源信息
     */
    @DeleteMapping("/deleteResource/{resId}")
    public ResponseEntity<Void> deleteResource(@PathVariable("resId") String resId){
        adminService.removeResource(resId);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --查询所有在线资源信息
     */
    @GetMapping("/getResourceWebInfo")
    public ResponseEntity<PageInfo<ResWeb>> getResourceWeb(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                           @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                           @RequestParam(value = "search", required = false) String search){
        PageInfo<ResWeb> resourceInfo = adminService.findResourceWebInfo(page, size, search);
        return ResponseEntity.ok(resourceInfo);
    }

    /**
     * 后台管理管理-资源管理 --增加或修改网络资源信息
     */
    @PostMapping("/updateOrInsertResourceWeb")
    public ResponseEntity<Void> updateOrInsertResourceWeb(@RequestBody  ResWeb resWeb){
        adminService.updateOrInsertResourceWeb(resWeb);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --根据Id删除网络资源信息
     */
    @DeleteMapping("/deleteResourceWeb/{resWebId}")
    public ResponseEntity<Void> deleteResourceWeb(@PathVariable("resWebId") String resWebId){
        adminService.removeResourceWeb(resWebId);
        return ResponseEntity.ok().build();
    }


    /**
     * 后台管理管理-资源管理 --查询所有未审核网盘资源信息
     */
    @GetMapping("/getResourceAuditInfo")
    public ResponseEntity<PageInfo<ResourceAudit>> getResourceAudit(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                    @RequestParam(value = "search", required = false) String search){
        PageInfo<ResourceAudit> resourceInfo = adminService.findResourceAuditInfo(page, size, search);
        return ResponseEntity.ok(resourceInfo);
    }

    /**
     * 后台管理管理-资源管理 --审核通过网盘资源信息
     */
    @PostMapping("/approvedResAudit")
    public ResponseEntity<Void> approvedResAudit(@RequestBody String resJson){
        ResourceAudit  resourceAudit = JSONObject.parseObject(resJson, ResourceAudit.class);
        adminService.approvedResAudit(resourceAudit);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --审核未通过删除该网盘资源信息
     */
    @DeleteMapping("/unApprovedResAudit/{resId}")
    public ResponseEntity<Void> unApprovedResAudit(@PathVariable("resId") String resId){
        adminService.unApprovedResAudit(resId);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --查询所有未审核在线资源信息
     */
    @GetMapping("/getResourceWebAuditInfo")
    public ResponseEntity<PageInfo<ResWebAudit>> getResourceWebAudit(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                     @RequestParam(value = "search", required = false) String search){
        PageInfo<ResWebAudit> resourceInfo = adminService.findResourceWebAuditInfo(page, size, search);
        return ResponseEntity.ok(resourceInfo);
    }

    /**
     * 后台管理管理-资源管理 --审核通过在线资源信息
     */
    @PostMapping("/approvedResWebAudit")
    public ResponseEntity<Void> approvedResWebAudit(@RequestBody String resWebJson){
        ResWebAudit resWebAudit = JSONObject.parseObject(resWebJson, ResWebAudit.class);
        adminService.approvedResWebAudit(resWebAudit);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-资源管理 --审核未通过删除该网络资源信息
     */
    @DeleteMapping("/unApprovedResWebAudit/{id}")
    public ResponseEntity<Void> unApprovedResWebAudit(@PathVariable("id") String id){
        adminService.unApprovedResWebAudit(id);
        return ResponseEntity.ok().build();
    }

}
