package work.niter.wecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.service.AccountService;

/**
 * @Author: Cangwu
 * @Date: 2019/9/26 17:35
 * @Description:
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PutMapping
    public ResponseEntity<Void> updateMyPassword(
            Account account,
            @RequestParam String stuEmail,
            @RequestParam String stuCode) {
        accountService.updateAccountPassword(account, stuEmail, stuCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> checkMyFirstLogin(String stuId) {
        return ResponseEntity.ok(accountService.checkPassword(stuId));
    }
}
