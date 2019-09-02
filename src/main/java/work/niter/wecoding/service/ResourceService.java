package work.niter.wecoding.service;

import com.github.pagehelper.PageHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.ResMore;
import work.niter.wecoding.entity.ResWeb;
import work.niter.wecoding.entity.Resource;
import work.niter.wecoding.mapper.ResMoreMapper;
import work.niter.wecoding.mapper.ResWebMapper;
import work.niter.wecoding.mapper.ResourceMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/7 1:17
 * @Description:
 */
@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ResMoreMapper resMoreMapper;
    @Autowired
    private ResWebMapper resWebMapper;

    public List<Resource> getLanguageResInService(String resType, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return resourceMapper.getResources(resType);
    }

    @Transactional(rollbackFor = Exception.class)
    public int resCountPlusInService(Integer resId) {
        ResMore resMore = resMoreMapper.selectByPrimaryKey(resId);
        resMore.setResHeat(resMore.getResHeat() + 1);
        return resMoreMapper.updateByPrimaryKeySelective(resMore);
    }

    public List<Resource> getResBySearchName(String resName, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return resourceMapper.getSearchResByNameService(resName);
    }

    @Cacheable(value = "res", key = "'web_' + #resWebType")
    public List<ResWeb> getLanguageWebService(String resWebType) {
        return resWebMapper.getResourcesWeb(resWebType);
    }

    /**
     * 每天三点检查资源是否仍有效
     * 秒 分 时 日 月 周几
     * *  *  *  *  *  *
     */
    @Scheduled(cron = "0 0 3 * * *")
    @Async("taskExecutor")
    public void checkResourcesStatus() {
        List<Resource> resources = resourceMapper.selectAll();
        resources.forEach(resource -> {
            try {
                Document document = Jsoup.connect("https://" + resource.getResUrl()).get();
                Element element = document.getElementById("share_nofound_des");
                if (element != null) {
                    System.out.println("RESID" + resource.getResId() + "Error ::: URL is :" + resource.getResUrl());
                    ResMore resMore = new ResMore();
                    resMore.setResId(resource.getResId());
                    resMore.setResStatus(0);
                    resMoreMapper.updateByPrimaryKeySelective(resMore);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
