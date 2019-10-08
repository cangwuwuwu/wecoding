package work.niter.wecoding.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.niter.wecoding.entity.CompSpend;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.service.CompService;

/**
 * @Author: Cangwu
 * @Date: 2019/9/26 18:01
 * @Description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    @Autowired
    private CompService compService;

    @PostMapping("/spend")
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

    @DeleteMapping("/spend")
    public ResponseEntity<Void> deleteMoneyImg(@RequestBody CompSpend spend) {
        try {
            storageClient.deleteFile("wecoding", spend.getSmall());
            storageClient.deleteFile("wecoding", spend.getBig());
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.DELETE_FAILED_FAILED);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/head")
    public ResponseEntity<CompStudent> uploadHeadImgBlob(
            @RequestParam MultipartFile file,
            @RequestParam Integer id) throws Exception {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(), file.getSize(), "png", null);
        String bigPath = storePath.getPath();
        String thumbPath = thumbImageConfig.getThumbImagePath(storePath.getPath());

        CompStudent student = new CompStudent();
        student.setId(id);
        student.setStuImg(thumbPath);
        student.setStuBigImg(bigPath);
        compService.updateStuInfoById(student);
        return ResponseEntity.ok(student);
    }
}
