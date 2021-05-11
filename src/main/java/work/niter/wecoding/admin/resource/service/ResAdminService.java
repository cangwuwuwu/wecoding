package work.niter.wecoding.admin.resource.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.msg.service.MailService;
import work.niter.wecoding.res.entity.*;
import work.niter.wecoding.res.mapper.*;

import java.util.Date;
import java.util.List;

/**
 * @author CangWu
 * @date 2019/11/27 10:51
 * @description
 */
@Service
public class ResAdminService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResMoreMapper resMoreMapper;

    @Autowired
    private ResWebMapper resWebMapper;

    @Autowired
    private ResAuditMapper resAuditMapper;

    @Autowired
    private ResWebAuditMapper resWebAuditMapper;

    @Autowired
    private MailService mailService;

    /**
     * 后台管理管理-资源管理 --查询所有的资源信息
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Resource> findResourceInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<Resource> resources = null;
        if (StringUtils.isNotBlank(search)){
            resources = resourceMapper.searchResource(search);
        }else {
            resources = resourceMapper.getAllResourceService();
        }
        if (CollectionUtils.isEmpty(resources)){
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);
        return pageInfo;
    }

    /**
     * 后台管理管理-资源管理 --增加或修改资源信息
     * @param resource
     * @return
     */
    @Transactional
    public void updateOrInsertResource(Resource resource) {
        Resource record = new Resource();
        ResMore resMore = new ResMore();
        resource.setResUpTime(new Date());
        resMore.setResId(resource.getResId());
        resMore.setResStatus(1);
        resMore.setResHeat(0);
        resMore.setResPoint(0d);
        record.setResId(resource.getResId());
        int count = resourceMapper.selectCount(record);
        if (count != 1){
            resourceMapper.insertSelective(resource);
            resMoreMapper.insertSelective(resMore);
        }else {
            resourceMapper.updateByPrimaryKey(resource);
        }
    }

    /**
     * 后台管理管理-资源管理 --根据Id删除资源信息
     */
    @Transactional
    public void removeResource(String resId) {
        if (resId != null){
            resourceMapper.deleteByPrimaryKey(resId);
            resMoreMapper.deleteByPrimaryKey(resId);
        }else {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }


    /**
     * 后台管理管理-资源管理 --查询所有的在线资源信息
     * @param page
     * @param size
     * @return
     */
    public PageInfo<ResWeb> findResourceWebInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<ResWeb> resWeb = null;
        if (StringUtils.isNotBlank(search)){
            resWeb = resWebMapper.searchResourceWeb(search);
        }else {
            resWeb = resWebMapper.getAllResWeb();
        }
        if (CollectionUtils.isEmpty(resWeb)){
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        PageInfo<ResWeb> pageInfo = new PageInfo<>(resWeb);
        return pageInfo;
    }

    /**
     * 后台管理管理-资源管理 --增加或修改在线资源信息
     * @param resWeb
     * @return
     */
    @Transactional
    public void updateOrInsertResourceWeb(ResWeb resWeb) {
        ResWeb record = new ResWeb();

        record.setId(resWeb.getId());
        int count = resWebMapper.selectCount(record);
        if (count != 1){
            resWebMapper.insertSelective(resWeb);
        }else {
            resWebMapper.updateByPrimaryKey(resWeb);
        }
    }

    /**
     * 后台管理管理-资源管理 --根据Id删除资源信息
     */
    @Transactional
    public void removeResourceWeb(String resWebId) {
        if (resWebId != null){
            resWebMapper.deleteByPrimaryKey(resWebId);
        }else {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }


    /**
     * 后台管理管理-资源管理 --查询所有未审核网盘资源信息
     * @param page
     * @param size
     * @return
     */
    public PageInfo<ResourceAudit> findResourceAuditInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<ResourceAudit> resources = null;
        if (StringUtils.isNotBlank(search)){
            resources = resAuditMapper.searchResourceAudit(search);
        }else {
            resources = resAuditMapper.selectAll();
        }
//        if (CollectionUtils.isEmpty(resources)){
//            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
//        }
        PageInfo<ResourceAudit> pageInfo = new PageInfo<>(resources);
        return pageInfo;
    }

    /**
     * 后台管理管理-资源管理 --审核通过网盘资源信息
     * @param resourceAudit
     */
    @Transactional
    public void approvedResAudit(ResourceAudit resourceAudit) {
        if (resourceAudit == null){
            return;
        }
        //将通过的资源存入资源表中
        resourceAudit.setResId(null);
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceAudit, resource);
        int i = resourceMapper.insertSelective(resource);
        //删除在审核表中的资源
        resAuditMapper.delete(resourceAudit);
        //将审核通过的信息通过邮件发送给资源上传者
        mailService.sendMailForRes(resourceAudit.getResUploader(),resourceAudit.getResName(), resourceAudit.getResType(),"审核通过", resourceAudit.getResUpEmail());
    }

    /**
     * 后台管理管理-资源管理 --审核未通过并删除该网盘资源信息
     * @param resId
     */
    @Transactional
    public void unApprovedResAudit(String resId) {
        if (StringUtils.isBlank(resId)){
            return;
        }
        ResourceAudit audit = resAuditMapper.selectByPrimaryKey(resId);
        resAuditMapper.deleteByPrimaryKey(resId);
        //将审核未通过的信息通过邮件发送给资源上传者
        mailService.sendMailForRes(audit.getResUploader(), audit.getResName(), audit.getResType(), "审核通未过", audit.getResUpEmail());
    }

    /**
     * 后台管理管理-资源管理 --查询所有未审核在线资源信息
     * @param page
     * @param size
     * @return
     */
    public PageInfo<ResWebAudit> findResourceWebAuditInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<ResWebAudit> resWebAudits = null;
        if (StringUtils.isNotBlank(search)){
            resWebAudits = resWebAuditMapper.searchResourceAuditWeb(search);
        }else {
            resWebAudits = resWebAuditMapper.selectAll();
        }
//        if (CollectionUtils.isEmpty(resWebAudits)){
//            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
//        }
        PageInfo<ResWebAudit> pageInfo = new PageInfo<>(resWebAudits);
        return pageInfo;
    }

    /**
     * 后台管理管理-资源管理 --审核通过在线资源信息
     * @param resWebAudit
     */
    @Transactional
    public void approvedResWebAudit(ResWebAudit resWebAudit) {
        if (resWebAudit == null){
            return;
        }
        //将审核通过的资源存入资源表中
        resWebAudit.setId(null);
        ResWeb resWeb = new ResWeb();
        BeanUtils.copyProperties(resWebAudit, resWeb);
        resWebMapper.insertSelective(resWeb);
        //删除在审核表中的资源
        resWebAuditMapper.delete(resWebAudit);
        //将审核通过的信息通过邮件发送给资源上传者
        mailService.sendMailForRes(resWebAudit.getResWebUper(),resWebAudit.getResWebName(), resWebAudit.getResWebType(), "审核通过", resWebAudit.getResWebEmail());
    }

    /**
     * 后台管理管理-资源管理 --审核未通过并删除该网络资源信息
     * @param id
     */
    @Transactional
    public void unApprovedResWebAudit(String id) {
        if (StringUtils.isBlank(id)){
            return;
        }
        ResWebAudit audit = resWebAuditMapper.selectByPrimaryKey(id);
        resWebAuditMapper.deleteByPrimaryKey(id);
        //将审核未通过的信息通过邮件发送给资源上传者
        mailService.sendMailForRes(audit.getResWebUper(),audit.getResWebName(),audit.getResWebType(), "审核未通过",audit.getResWebEmail());
    }
}
