package work.niter.wecoding.admin.account.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.user.entity.Account;
import work.niter.wecoding.user.entity.CompStudent;
import work.niter.wecoding.user.mapper.AccountMapper;
import work.niter.wecoding.user.mapper.CompMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhai
 * @date 2019/11/27 11:14
 * @description
 */
@Service
public class AccountAdminService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CompMapper compMapper;

    /**
     * 后台管理管理-账户管理 --根据搜索字段查询所有账户信息
     */
    @Transactional
    public PageInfo<Account> findInfoWithSearch(Integer page, Integer size, String search){
        PageHelper.startPage(page, size);
        List<CompStudent> students = null;
        if (StringUtils.isNotBlank(search)) {
            students = compMapper.findInfoWithSearchAndSort(search);
        }else {
            students = compMapper.findInfoAndSort();
        }
        PageInfo<CompStudent> info = new PageInfo<>(students);
        List<Account> accounts = new ArrayList<>();
        students.forEach(s -> {
            Account account = accountMapper.selectByPrimaryKey(s.getStuId());
            if (account == null){
                throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
            }
            account.setStuName(s.getStuName());
            account.setStuPhone(s.getStuPhone());
            switch (account.getStuAuth()) {
                case 0:
                    account.setStuAuthString("普通用户");
                    account.setStuDesc("普通用户");
                    break;
                case 1:
                    account.setStuAuthString("管理员");
                    account.setStuDesc("管理员");
                    break;
                case 2:
                    account.setStuAuthString("超级管理员");
                    account.setStuDesc("超级管理员");
            }
            accounts.add(account);

        });
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        pageInfo.setTotal(info.getTotal());
        pageInfo.setPageSize(info.getPageSize());
        pageInfo.setPageNum(info.getPageNum());
        return pageInfo;
    }



    /**
     * 后台管理管理-账户管理 --查询所有权限名称
     */
    public List<Map<String, String>> findAllAuth() {

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> map3 = new HashMap<>();
        map1.put("value", "普通用户");
        map1.put("label", "普通用户");
        map2.put("value", "管理员");
        map2.put("label", "管理员");
        map3.put("value", "超级管理员");
        map3.put("label", "超级管理员");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }

    /**
     * 后台管理管理-账户管理 --根据学号修改权限
     */
    @PreAuthorize("hasRole('SUPER')")
    @Transactional
    public void updateAuth(String stuId, String stuAuthString) {
        Account account = new Account();
        account.setStuId(stuId);
        switch (stuAuthString){
            case "普通用户":
                account.setStuAuth(0);
                break;
            case "管理员":
                account.setStuAuth(1);
                break;
            case "超级管理员":
                account.setStuAuth(2);
                break;
            default:
                throw new RestException(ExceptionEnum.ARGS_NOT_FOUND_ERROR);
        }
        accountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 后台管理管理-账户管理 --根据学号删除账号
     */
    @PreAuthorize("hasRole('SUPER')")
    @Transactional
    public void removeAccount(String stuId) {
        if (stuId == null){
            throw new RestException(ExceptionEnum.ARGS_NOT_FOUND_ERROR);
        }
        //在账号权限表中删除该记录
        accountMapper.deleteByPrimaryKey(stuId);
        //在学生表中删除该记录
        CompStudent compStudent = new CompStudent();
        compStudent.setStuId(stuId);
        compMapper.delete(compStudent);
    }

}
