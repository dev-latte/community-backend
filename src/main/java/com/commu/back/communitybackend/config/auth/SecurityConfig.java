package com.commu.back.communitybackend.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableWebSecurity
@Configuration
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                        .mvcMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers("/", "/login/**", "/twitter/complete", "/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION")
                        .invalidateHttpSession(true);
    }
}
