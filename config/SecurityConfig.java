package org.zerock.myapp.config;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.zerock.myapp.service.UsersDetailsServiceImpl;

import java.util.Objects;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Log4j2
@NoArgsConstructor
@EnableWebSecurity

@Configuration
public class SecurityConfig {

    @Setter(onMethod_ = @Autowired)
    private UsersDetailsServiceImpl usersDetailsService;

//    1. 만든 UserDetailsService 객체를 이용해 특정 사용자의 인증정보를 사용하도록 하는 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception{
        log.trace("securityFilterChain({}) Invoked.", http);
        Objects.requireNonNull(this.usersDetailsService);
        http.userDetailsService(this.usersDetailsService);

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests(
                customizer ->
                    customizer.requestMatchers(antMatcher("/main")).permitAll().
                            requestMatchers(antMatcher("/recipe/write")).hasAnyRole("ADMIN","USER").
                            requestMatchers(antMatcher("/noticeWriter")).hasRole("ADMIN").
                            anyRequest().permitAll()
                );


        http.csrf(AbstractHttpConfigurer::disable);

//        http.formLogin(
//                customizer -> customizer.loginPage("/login/login").
//                        loginProcessingUrl("/login").
//                        defaultSuccessUrl("/main",true)
//        );

        http.formLogin(
                customizer -> customizer.loginPage("/login/login").
                        loginProcessingUrl("/login").
                        usernameParameter("userId").
                        passwordParameter("password").
                        defaultSuccessUrl("/main", true)
        );

        http.exceptionHandling(customizer -> customizer.accessDeniedPage("/accessDenied"));



        http.logout( customizer ->
                customizer.invalidateHttpSession(true).
                        logoutSuccessUrl("/main")
        ); // logout

        return http.build();
    } // securityFilterChain


    //    Password Encoding암호화!
    @Bean
    public PasswordEncoder passwordEncoder(){
        log.trace("passwordEncoder() Invoked.");

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    } // passwordEncoder




} // end class
