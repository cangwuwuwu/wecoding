package work.niter.wecoding.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.user.entity.Account;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.user.mapper.AccountMapper;

/**
 * @author Cangwu
 * @date 2019/7/14 1:32
 * @description
 */
@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Account getAccountById(String id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAccountPassword(Account account, String email, String stuCode) {
        String code = redisTemplate.opsForValue().get(email);
        if (stuCode.equals(code)) {
            String password = account.getStuPassword();
            account.setStuPassword(new BCryptPasswordEncoder().encode(password));
            int i = accountMapper.updateByPrimaryKeySelective(account);
            if (i == 0) {
                throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
            }
        } else {
            throw new RestException(ExceptionEnum.CODE_MATCH_ERROR);
        }
    }

    public int checkPassword(String stuId) {
        String defaultPassword = "123456";
        // 查询旧密码
        Account account = accountMapper.selectByPrimaryKey(stuId);
        // encode.matches比对密码是否相等
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        if (encode.matches(defaultPassword, account.getStuPassword())) {
            return 1;
        } else {
            return 0;
        }
    }
}
