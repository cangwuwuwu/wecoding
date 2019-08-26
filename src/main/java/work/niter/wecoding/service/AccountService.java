package work.niter.wecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.mapper.AccountMapper;

import java.security.Principal;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:32
 * @Description:
 */
@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account getAccountByUsernameInService(String username) {
        return accountMapper.selectByPrimaryKey(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUserPasswordInService(Account account) {
        accountMapper.addUserPassword(account);
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateAccountPassword(Account account, String newPassword) {
        // 查询旧密码
        Account oldaccount = accountMapper.selectByPrimaryKey(account.getStuUsername());
        // encode.matches比对密码是否相等
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        if (encode.matches(account.getStuPassword(), oldaccount.getStuPassword())) {
            String bcryptPasswd = (encode.encode(newPassword));
            oldaccount.setStuPassword(bcryptPasswd);
            // 更新新密码
            return accountMapper.updateByPrimaryKey(oldaccount);
        }
        return -1;
    }
}
