package work.niter.wecoding.config;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.authenticationProvider(authenticationProvider())
                .httpBasic()
                // 未登录/无权限 提示
                .authenticationEntryPoint(((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("code", 403);
                    map.put("message", "未登录");
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                }))
                .and()*/
                    .authorizeRequests()
                    .antMatchers("/login/**", "/signup/**").permitAll()
                    .antMatchers("/submit", "/sendmail/**", "/stu/**", "/resources/**").permitAll()
                    .antMatchers("/delete**", "/admin**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")
                    .loginProcessingUrl("/login")
                .usernameParameter("stuUsername")
                .passwordParameter("stuPassword")
                .failureHandler(((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<>(4);
                    map.put("status", 401);
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        map.put("message","用户名或密码错误");
                    } else if (e instanceof CredentialsExpiredException) {
                        map.put("message","登录过期");
                    } else {
                        map.put("message","登录失败!");
                    }
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                }))
                .successHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<>(5);
                    map.put("status",200);
                    map.put("message","登录成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<>(4);
                    map.put("status",403);
                    map.put("message", "权限不足");
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                })
                /*.and()
                    .rememberMe()
                    .tokenRepository(this.persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .userDetailsService(this.userDetailService)*/
                .and()
                    .logout()
                //退出成功，返回json
                .logoutSuccessHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<>(5);
                    map.put("status",200);
                    map.put("message","退出成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                })
                /*.and()
                    .sessionManagement()
                    .invalidSessionUrl("/login/timeout")*/
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

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
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
