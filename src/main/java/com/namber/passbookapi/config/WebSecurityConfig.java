package com.namber.passbookapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/info/**").permitAll()
                .antMatchers("/user-register").permitAll()
                .anyRequest().authenticated();
        http.httpBasic();
        http.csrf().disable(); // for post endpoints to work with basic, disable csrf [insecure method, learn more on csrf]
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
