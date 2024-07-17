package com.example.book_api.configurations;

import com.example.book_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final String [] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/**",
            "/notification/**",
            "/swagger-resources",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-resources/configuration/ui"
    };
    private final String [] AllRole_ENDPOINTS = {

    };
    private final String [] USER_ENDPOINTS = {
            "/api/v1/user/**"
    };
    private final String [] ADMIN_ENDPOINTS = {
            "/api/v1/admin/**"
    };

    @Autowired
    UserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers(AllRole_ENDPOINTS).hasAnyRole("Admin","User")
                .antMatchers(ADMIN_ENDPOINTS).hasAuthority("Admin")
                .antMatchers(USER_ENDPOINTS).hasAuthority("User")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authJWTFilter(), UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    AuthJWTFilter authJWTFilter() {
        return new AuthJWTFilter();
    }



}
