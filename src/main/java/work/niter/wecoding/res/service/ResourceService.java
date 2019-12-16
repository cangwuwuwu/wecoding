package work.niter.wecoding.res.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.res.entity.ResMore;
import work.niter.wecoding.res.entity.ResWeb;
import work.niter.wecoding.res.entity.Resource;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.res.entity.ResourceAudit;
import work.niter.wecoding.res.mapper.ResAuditMapper;
import work.niter.wecoding.res.mapper.ResMoreMapper;
import work.niter.wecoding.res.mapper.ResWebMapper;
import work.niter.wecoding.res.mapper.ResourceMapper;

import java.io.IOException;
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
    @Autowired
    private ResAuditMapper resAuditMapper;

    /**
     * 分页查询资源列表
     * @param resType
     * @param page
     * @param limit
     * @return
     */
    public PageInfo<Resource> getLanguageResInService(
            String resType, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Resource> resources = resourceMapper.getResources(resType);
        return new PageInfo<>(resources);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resCountPlusInService(Integer resId) {
        ResMore resMore = resMoreMapper.selectByPrimaryKey(resId);
        resMore.setResHeat(resMore.getResHeat() + 1);
        int i = resMoreMapper.updateByPrimaryKeySelective(resMore);
        if (i == 0) {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    public PageInfo<Resource> getResBySearchName(String resName, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Resource> resources = resourceMapper.getSearchResByNameService(resName);
        return new PageInfo<>(resources);
    }

    @Cacheable(value = "res", key = "'web_' + #resWebType")
    public List<ResWeb> getLanguageWebService(String resWebType) {
        return resWebMapper.getResourcesWeb(resWebType);
    }

    public void uploadResource(ResourceAudit resource) {
        int i = resAuditMapper.insertSelective(resource);
        if (i == 0) {
            throw new RestException(ExceptionEnum.RESOURCE_UPLOAD_FAILED);
        }
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


    /**
     * 每周检查是否有未清理的过期资源并通知管理员
     */

    /**
     * 每天检查是否有用户过生日发送生日祝福
     */


}
