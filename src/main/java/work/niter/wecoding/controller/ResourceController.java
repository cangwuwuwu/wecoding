package work.niter.wecoding.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.Resource;
import work.niter.wecoding.service.ResourceService;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/7 0:54
 * @Description:
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/language/{language}")
    public PageInfo<Resource> getLanguageRes(@PathVariable String language,
                                             @RequestParam(value="page", defaultValue = "1") Integer page,
                                             @RequestParam(value="limit", defaultValue = "10") Integer limit) {

        List<Resource> reslist = resourceService.getLanguageResInService(language, page, limit);
        return new PageInfo<>(reslist);
    }
}
