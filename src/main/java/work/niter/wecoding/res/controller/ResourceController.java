package work.niter.wecoding.res.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.res.entity.ResRate;
import work.niter.wecoding.res.entity.ResWeb;
import work.niter.wecoding.res.entity.Resource;
import work.niter.wecoding.res.entity.ResourceAudit;
import work.niter.wecoding.res.service.ResRateService;
import work.niter.wecoding.res.service.ResourceService;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/7 0:54
 * @Description:
 */
@RestController
@RequestMapping("/res")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResRateService resRateService;

    @GetMapping("/video/{language}")
    public ResponseEntity<PageInfo<Resource>> getLanguageRes(
            @PathVariable String language,
            @RequestParam(value="page", defaultValue = "1") Integer page,
            @RequestParam(value="limit", defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(resourceService.getLanguageResInService(language, page, limit));
    }

    @PostMapping
    public ResponseEntity<Void> postLanguageRes(ResourceAudit resource) {
        resourceService.uploadResource(resource);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/web/{language}")
    public ResponseEntity<List<ResWeb>> getLanguageWeb(@PathVariable String language) {
        return ResponseEntity.ok(resourceService.getLanguageWebService(language));
    }

    @GetMapping("/search")
    public ResponseEntity<PageInfo<Resource>> getResBySearchName(
            @RequestParam(value = "resName") String resName,
            @RequestParam(value="page", defaultValue = "1") Integer page,
            @RequestParam(value="limit", defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(resourceService.getResBySearchName(resName, page, limit));
    }

    @PostMapping("/count")
    public ResponseEntity<Void> resCountPlus(@RequestParam(value = "resId") Integer resId) {
        resourceService.resCountPlusInService(resId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rate")
    public ResponseEntity<Void> putResRate(ResRate resRate) {
        resRateService.newRate(resRate);
        return ResponseEntity.ok().build();
    }
}
