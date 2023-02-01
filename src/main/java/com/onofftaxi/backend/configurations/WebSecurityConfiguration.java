package com.onofftaxi.backend.configurations;

import com.onofftaxi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final int BCRYPT_STRENGTH = 14;

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/icons/**")
                .antMatchers("/images/**")
                .antMatchers("/styles/**")
                .antMatchers("/frontend/**")
                .antMatchers("/VAADIN/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                /*.requestMatchers(SecurityUtils::isFrameworkInternalRequest)*/
                .antMatchers("/","/contact","/reklama","/regulamin").permitAll()// <-------------------------- tu po przecinku możesz dorzucać endpointy
                .antMatchers("/register","/forgotpassword").permitAll()

                .antMatchers("https://www.facebook.com//on-offtaxi").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/driver/*","/info").hasRole("DRIVER")
                .antMatchers("/anonymous*").anonymous()
                .and().formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/login").failureUrl("/login?error")
                .successForwardUrl("/driver/account")
                .and().rememberMe().key("uniqueAndSecret").alwaysRemember(true).userDetailsService(userService)
                // Configure logout
                .and().logout().logoutSuccessUrl("/")
                .logoutUrl("/perform_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
