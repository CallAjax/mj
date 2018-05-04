package cn.codesign.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Resource
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定义无需验证URL范围
        http.csrf().disable().authorizeRequests().antMatchers(
                "/","/service/manage/login","/service/image"
        ).permitAll()
                //所有请求需要登录
                .anyRequest().authenticated()
                //定义提交验证用户名和密码字段名称
                .and().formLogin().usernameParameter("uid").passwordParameter("pwd")
                //定义登陆URL和login提交URL以及成功处理
                .loginPage("/").loginProcessingUrl("/service/manage/login").successHandler(ajaxAuthenticationSuccessHandler)
                //登录失败处理
                .failureHandler(ajaxAuthenticationFailureHandler)
                .and().exceptionHandling().authenticationEntryPoint(ajaxAuthenticationEntryPoint)
                .and().addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }

}
