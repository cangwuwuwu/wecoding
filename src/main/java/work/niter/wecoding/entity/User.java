package work.niter.wecoding.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @Author: Cangwu
 * @Date: 2019/9/27 14:08
 * @Description: 自定义UserDetails
 */
@Data
public class User implements UserDetails {

    private String username;
    private String password;
    private String stuName;
    private List<GrantedAuthority> authorities;
    private String stuEmail;
    private String stuImg;

    public User(
            String stuId,
            String stuPassword,
            String stuName,
            List<GrantedAuthority> stuRoles,
            String stuEmail,
            String stuImg) {
        this.username = stuId;
        this.password = stuPassword;
        this.stuName = stuName;
        this.authorities = stuRoles;
        this.stuEmail = stuEmail;
        this.stuImg = stuImg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.stuName).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");
        if (!this.authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            Iterator var3 = this.authorities.iterator();

            while(var3.hasNext()) {
                GrantedAuthority auth = (GrantedAuthority)var3.next();
                if (!first) {
                    sb.append(",");
                }

                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
