package work.niter.wecoding.admin.account.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.admin.access.annotation.PageView;
import work.niter.wecoding.admin.account.service.AccountAdminService;
import work.niter.wecoding.user.entity.Account;

import java.util.List;
import java.util.Map;

/**
 * @author CangWu
 * @date 2019/11/27 11:11
 * @description
 */
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/comp/account")
@RestController
public class AccountAdminController {

    @Autowired
    private AccountAdminService adminService;

    /**
     * 后台管理管理-账户管理 --分页插叙所有账户信息
     */
    @GetMapping("getAccountInfo")
    public ResponseEntity<PageInfo<Account>> getAllAccountInfo(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                               @RequestParam(value = "search", required = false) String search){
        return ResponseEntity.ok(adminService.findInfoWithSearch(page, size, search));
    }



    /**
     * 后台管理管理-账户管理 --查询所有权限名称
     */
    @GetMapping("getAllAuth")
    public ResponseEntity<List<Map<String, String>>> getAllAuth(){
        return ResponseEntity.ok(adminService.findAllAuth());
    }

    /**
     * 后台管理管理-账户管理 --根据学号修改权限
     */
    @PutMapping("/updateAuth")
    public ResponseEntity<Void> putUpdateAuth(@RequestParam("stuId")String stuId, @RequestParam("stuAuth")String stuAuthString){
        adminService.updateAuth(stuId, stuAuthString);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-账户管理 --根据学号删除账号
     */
    @DeleteMapping("/deleteAccount/{stuId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("stuId") String stuId){
        adminService.removeAccount(stuId);
        return ResponseEntity.ok().build();
    }

}
