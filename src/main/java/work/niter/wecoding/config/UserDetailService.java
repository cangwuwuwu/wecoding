package work.niter.wecoding.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.entity.User;
import work.niter.wecoding.service.AccountService;
import work.niter.wecoding.service.CompService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:05
 * @Description:
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CompService compService;

    @Override
    public UserDetails loadUserByUsername(String stuId) throws UsernameNotFoundException {
        Account account = accountService.getAccountById(stuId);
        CompStudent student = compService.getStudentById(stuId);
        if (account != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (account.getStuAuth() == 1) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(
                    stuId,
                    account.getStuPassword(),
                    student.getStuName(),
                    authorities,
                    student.getStuEmail(),
                    student.getStuImg());
        } else {
            throw new UsernameNotFoundException("User '" + stuId + "' not found.");
        }
    }
}