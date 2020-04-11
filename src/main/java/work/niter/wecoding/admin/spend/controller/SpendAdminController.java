package work.niter.wecoding.admin.spend.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.admin.spend.service.SpendAdminService;
import work.niter.wecoding.alipay.entity.Payment;
import work.niter.wecoding.spend.entity.CompSpend;

import javax.script.ScriptException;

/**
 * @Author xiaozhai
 * @Date 2019/11/27 11:02
 * @Description:
 */
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/comp/spend")
@RestController
public class SpendAdminController {

    @Autowired
    private SpendAdminService adminService;

    /**
     * 后台管理管理-财务管理 --分页插叙所有财务收支信息
     */
    @GetMapping("getSpendInfo")
    public ResponseEntity<PageInfo<CompSpend>> getSpendInfo(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                            @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                            @RequestParam(value = "search", required = false) String search){
        PageInfo<CompSpend> spendInfo = adminService.findSpendInfo(page, size, search);
        return ResponseEntity.ok(spendInfo);
    }

    /**
     * 后台管理管理-财务管理 --查询协会经费余额
     */
    @GetMapping("getSpendBalance")
    public ResponseEntity<String> getSpendBalance() throws ScriptException {
        double balance = adminService.findSpendBalance();
        return ResponseEntity.ok(String.valueOf(balance));
    }

    /**
     * 后台管理管理-财务管理 --新增或修改财务收支信息
     */
    @PostMapping("/updateOrInsertSpend")
    public ResponseEntity<Void> updateOrInsertSpend(@RequestBody  CompSpend compSpend){
        adminService.updateOrInsertSpend(compSpend);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-财务管理 --删除指定的财务收支信息
     */
    @DeleteMapping("/deleteSpend/{id}")
    public ResponseEntity<Void> deleteSpendById(@PathVariable Integer id){
        adminService.removeSpend(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 后台管理管理-财务管理 --分页插叙所有会费缴纳信息
     */
    @GetMapping("getSpendDuesInfo")
    public ResponseEntity<PageInfo<Payment>> getSpendDuesInfo(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                                              @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                              @RequestParam(value = "search", required = false) String search){
        PageInfo<Payment> PaymentInfo = adminService.getSpendDuesInfo(page, size, search);
        return ResponseEntity.ok(PaymentInfo);
    }

    /**
     * 后台管理管理-财务管理 --手动添加会费缴纳信息
     */
    @PostMapping("updatePaymentInfo")
    public ResponseEntity<Void> updatePaymentInfo(@RequestBody Payment payment){
        adminService.insertPaymentInfo(payment);
        return ResponseEntity.ok().build();
    }

}
