package com.rocket.laf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.rocket.laf.service.UserService;
import com.rocket.laf.service.impl.UserServiceImpl;

@Configuration
public class SecurityConfig{

    //로그인 서비스 등록을 해줘야만 서비스임플리먼트에서 UserDetailsService가 작동한다.
    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @Bean
    public WebSecurityCustomizer webSecCustomizer(){
        return web -> web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain secFiltChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
            .headers()
                .frameOptions().disable().and()
            .authorizeRequests()
                .antMatchers("/**").permitAll()
                // .antMatchers("/user/signUp").permitAll()
                // .antMatchers("/user/signUpForm").permitAll()
                //.antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated().and()
            .formLogin()
                .loginPage("/user/login").permitAll()
                .defaultSuccessUrl("/")
                .failureForwardUrl("/user/login")
                .and()
            .logout()
                .logoutUrl("/user/logout").and()
            .build();

    }

    //UserDetailsService에서 실행될 AuthenticationManager 생성.
    @Bean
    public AuthenticationManager authMng(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    //UserDetailsService 실행시 encryption 객체없으면 에러남
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    
}
