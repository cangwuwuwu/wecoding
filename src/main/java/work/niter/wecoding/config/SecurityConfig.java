package work.niter.wecoding.config;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import work.niter.wecoding.entity.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:05
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    UserDetailService userDetails() {
        return new UserDetailService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> this.exceptionReturn(
                            response, e, 401, HttpServletResponse.SC_UNAUTHORIZED, "未登录或登录过期"))
                .and()
                    .authorizeRequests()
                    .antMatchers("/login/**", "/signup/**").permitAll()
                    .antMatchers("/sendmail/**", "/res/**").permitAll()
                    .antMatchers("/stu/id/**", "/stu/getPart").permitAll()
                    .antMatchers("/comp").permitAll()
                    .antMatchers("/ele/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")
                    .loginProcessingUrl("/login")
                    .usernameParameter("stuId")
                    .passwordParameter("stuPassword")
                    .failureHandler(((request, response, e) -> {
                        String message;
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            message = "用户名或密码错误";
                        } else {
                            message = "登录失败";
                        }
                        this.exceptionReturn(response, e, 401, HttpServletResponse.SC_UNAUTHORIZED, message);
                    }))
                    .successHandler((request, response, authentication) ->
                            this.successReturn(response, authentication, "登录成功"))
                .and()
                    .exceptionHandling()
                    //没有权限，返回json
                    .accessDeniedHandler((request, response, e) -> this.exceptionReturn(
                                response, e, 403, HttpServletResponse.SC_FORBIDDEN, "权限不足"))
                .and()
                    .rememberMe()
                    .tokenRepository(this.persistentTokenRepository())
                    .tokenValiditySeconds(60*60*24)
                    .userDetailsService(this.userDetails())
                .and()
                    .logout()
                    .logoutSuccessHandler((request,response,authentication) ->
                        this.successReturn(response, authentication, "退出成功"))
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
        auth
                .userDetailsService(this.userDetails())
                .passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 未登录/无权访问
     */
    private void exceptionReturn(
            HttpServletResponse response,
            Exception e, Integer status,
            Integer type, String message) throws IOException {
        e.printStackTrace();
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(type);
        PrintWriter out = response.getWriter();
        Map<String,Object> map = new HashMap<>(4);
        map.put("status", status);
        map.put("message", message);
        out.write(JSON.toJSONString(map));
        out.flush();
        out.close();
    }

    /**
     * 登录或退出成功返回状态码和信息
     */
    private void successReturn(
            HttpServletResponse response, Authentication auth,
            String message) throws IOException {
        Map<String,Object> map = new HashMap<>(8);
        map.put("status", 200);
        map.put("message", message);
        map.put("data", auth);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(map));
        out.flush();
        out.close();
    }

}
