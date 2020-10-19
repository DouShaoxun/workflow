package cn.cruder.workflow.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security配置
 *
 * @Author: Dsx
 * @Date: 2020-10-18 20:28
 * @Description: description
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 授权请求
                .authorizeRequests()
                // 放行所有端口
                .anyRequest().permitAll()
                .and()
                //允许所有访问 退出接口
                .logout().permitAll()
                .and()
                // 关闭csrf
                .csrf().disable()
                // 允许frame框架
                .headers().frameOptions().disable();
    }

}
