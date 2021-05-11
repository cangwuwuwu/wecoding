package work.niter.wecoding.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.user.entity.Account;
import work.niter.wecoding.user.service.AccountService;

/**
 * @author Cangwu
 * @date 2019/9/26 17:35
 * @description
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
