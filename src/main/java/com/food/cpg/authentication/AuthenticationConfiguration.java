package com.food.cpg.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationByRoleSuccessHandler authenticationByRoleSuccessHandler;

    @Autowired
    private AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(AuthenticationQuery.USERS_BY_USERNAME)
                .authoritiesByUsernameQuery(AuthenticationQuery.AUTHORITIES_BY_USERNAME);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AuthenticationEndpoint.PUBLIC_END_POINTS)
                .permitAll()
                .antMatchers(AuthenticationEndpoint.ADMIN_END_POINTS)
                .hasAnyAuthority(Role.ADMIN.name())
                .anyRequest()
                .hasAnyAuthority(Role.MANUFACTURER.name())
                .and()
                .formLogin().loginPage(AuthenticationEndpoint.LOGIN_END_POINT).successHandler(authenticationByRoleSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedExceptionHandler)
                .and()
                .logout().permitAll();
    }
}
