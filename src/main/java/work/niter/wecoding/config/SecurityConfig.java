package work.niter.wecoding.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:05
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/login/**", "/index", "/signup/**", "/").permitAll()
                    .antMatchers("/home", "/guide", "/allstus", "/help","/chatroom").permitAll()
                    .antMatchers("/submit", "/sendmail/**", "/stu/**").permitAll()
                    .antMatchers("/css/**", "/images/**", "/js/**", "/md/**", "/fonts/**").permitAll()
                    .antMatchers("/delete**", "/admin**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")
                    .loginPage("/login")
                .and()
                    .rememberMe()
                    .tokenRepository(this.persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .userDetailsService(this.userDetailService)
                .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout")
                .and()
                    .sessionManagement()
                    .invalidSessionUrl("/login/timeout")
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .csrf().disable();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(this.dataSource);
        return persistentTokenRepository;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
