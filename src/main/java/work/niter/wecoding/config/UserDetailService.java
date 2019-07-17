package work.niter.wecoding.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.service.AccountService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:05
 * @Description:
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String stuUsername) throws UsernameNotFoundException {
        Account account = this.accountService.getAccountByUsernameInService(stuUsername);
        if (account != null) {
            List<GrantedAuthority> authorities = new ArrayList();
            if ("admin".equals(stuUsername)) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(
                    stuUsername,
                    account.getStuPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("User '" + stuUsername + "' not found.");
        }
    }
}