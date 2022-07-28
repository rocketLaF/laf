package com.rocket.laf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rocket.laf.service.UserService;
import com.rocket.laf.service.impl.UserServiceImpl;


@Configuration
public class SecurityConfig {

    // 로그인 서비스 등록을 해줘야만 서비스임플리먼트에서 UserDetailsService가 작동한다.
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Bean
    public WebSecurityCustomizer webSecCustomizer(){
        //나중에 공부해야함.
        return web -> web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain secFiltChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .headers()
                .frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/","/cBoard","/picture").permitAll()
//                .antMatchers("/user/**").permitAll()

                 .antMatchers("/cBoard/**").hasRole("USER")
                // .antMatchers("/user/signUp").permitAll()
                // .antMatchers("/user/signUpForm").permitAll()
                // .antMatchers("/user/chkDuplicatedId").permitAll()
                // .antMatchers("/cBoard").hasRole("USER")
                // .antMatchers("/user/signUp").permitAll()
                // .antMatchers("/user/signUpForm").permitAll()
                // .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/user/login").permitAll()
                // .defaultSuccessUrl("/")
                .successHandler(new UserServiceImpl())
                .failureUrl("/user/login?error=true")
                // .failureHandler(new failureclassname())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
            .oauth2Login()
                .loginPage("/user/login").permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error=true")
                .userInfoEndpoint()
                .userService(userServiceImpl)
                ;
            return http.build();

    }

    // UserDetailsService에서 실행될 AuthenticationManager 생성.
    @Bean
    public AuthenticationManager authMng(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // UserDetailsService 실행시 encryption 객체없으면 에러남
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}