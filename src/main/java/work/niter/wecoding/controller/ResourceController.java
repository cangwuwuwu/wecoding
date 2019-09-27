package work.niter.wecoding.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.ResWeb;
import work.niter.wecoding.entity.Resource;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.service.ResourceService;

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

    @GetMapping("/video/{language}")
    public ResponseEntity<PageInfo<Resource>> getLanguageRes(
            @PathVariable String language,
            @RequestParam(value="page", defaultValue = "1") Integer page,
            @RequestParam(value="limit", defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(resourceService.getLanguageResInService(language, page, limit));
    }

    @PostMapping
    public ResponseEntity<Void> postLanguageRes(Resource resource) {
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
}
