package work.niter.wecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.mapper.AccountMapper;

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
        return accountMapper.selectByStuUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUserPasswordInService(Account account) {
        accountMapper.addUserPassword(account);
    }
}
