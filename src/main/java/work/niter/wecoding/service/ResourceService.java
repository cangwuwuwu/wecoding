package work.niter.wecoding.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import work.niter.wecoding.entity.Resource;
import work.niter.wecoding.mapper.ResourceMapper;

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

    public List<Resource> getLanguageResInService(String resType, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return resourceMapper.getLanguageRes(resType);
    }
}
