package work.niter.wecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
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
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Account getAccountById(String id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAccountPassword(Account account,String email, String stuCode) {
        String code = redisTemplate.opsForValue().get(email);
        if (stuCode.equals(code)) {
            String password = account.getStuPassword();
            account.setStuPassword(new BCryptPasswordEncoder().encode(password));
            int i = accountMapper.updateByPrimaryKeySelective(account);
            if (i == 0) {
                throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
            }
        } else {
            throw new RestException(ExceptionEnum.CODE_MATCH_FAILD);
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
