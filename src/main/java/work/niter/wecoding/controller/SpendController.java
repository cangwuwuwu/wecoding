package work.niter.wecoding.controller;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.niter.wecoding.entity.CompSpend;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.service.SpendService;

/**
 * @Author: Cangwu
 * @Date: 2019/9/20 16:28
 * @Description: 财务收支模块
 */
@RestController
@RequestMapping("/comp/spend")
public class SpendController {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    @Autowired
    private SpendService spendService;

    @PostMapping("/uploadimg")
    public ResponseEntity<CompSpend> uploadMoneyImg(
            @RequestParam MultipartFile file
    ) throws Exception {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(), file.getSize(), "png", null);
        String smallPath = thumbImageConfig.getThumbImagePath(storePath.getPath());
        String bigPath = storePath.getPath();

        CompSpend spend = new CompSpend();
        spend.setBig(bigPath);
        spend.setSmall(smallPath);

        return ResponseEntity.ok(spend);
    }

    @DeleteMapping("/uploadimg")
    public ResponseEntity<Void> deleteMoneyImg(@RequestBody CompSpend spend) {
        try {
            storageClient.deleteFile("wecoding", spend.getSmall());
            storageClient.deleteFile("wecoding", spend.getBig());
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.DELETE_FAILED_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addSpend(@RequestBody CompSpend compSpend) {
        System.out.println(compSpend);
        spendService.insertSpend(compSpend);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageInfo<CompSpend>> getAllSpendByPage(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(spendService.findAllSpendByPage(page, size));
    }
}
