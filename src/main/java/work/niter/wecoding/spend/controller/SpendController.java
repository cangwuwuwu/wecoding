package work.niter.wecoding.spend.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.spend.entity.CompSpend;
import work.niter.wecoding.msg.service.MailService;
import work.niter.wecoding.spend.service.SpendService;

/**
 * @author Cangwu
 * @date 2019/9/20 16:28
 * @description 财务收支模块
 */
@RestController
@RequestMapping("/comp/spend")
public class SpendController {
    @Autowired
    private SpendService spendService;
    @Autowired
    private MailService mailService;


    @PostMapping
    public ResponseEntity<Void> addSpend(@RequestBody CompSpend compSpend) {
        spendService.insertSpend(compSpend);
        mailService.sendMailForSpend(
                compSpend.getName(),
                compSpend.getNumber());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageInfo<CompSpend>> getAllSpendByPage(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(spendService.findAllSpendByPage(page, size));
    }
}
