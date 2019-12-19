package com.expleo.webcm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

/**
 * Contains the configuration of security accesses.
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //add a referente to our security datasource
    @Autowired
    @Qualifier("securityDataSource")
    private DataSource dataSource;

    //a reference to customAuthenticationSuccessHandler
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler){
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // seaching in database to check username and roles of his
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "SELECT us.username, user_roles FROM roles INNER JOIN"+
                                " authorities as auth on id_roles=auth.authority INNER JOIN"+
                                " users as us on auth.username = us.id_user WHERE us.username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //filter to accept UTF-8 encoding
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        //setup of authorization and accesses by roles
        http.authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/webCM").hasAnyRole("EMPLOYEE","MANAGER")
                .antMatchers("/webCM/leaders/project/{codProiect}/**").access("@guard.checkCodProiect(#codProiect)") //to check if manager has project?????
                .antMatchers("/webCM/leaders/**").hasRole("MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/manager/**").hasRole("ADMIN")
                    .antMatchers("/forgotPassword/**").permitAll()
                    .antMatchers("/**").hasAnyRole("EMPLOYEE", "ADMIN", "MANAGER")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .successHandler(authenticationSuccessHandler)
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");


    }

    @Bean
    public UserDetailsManager userDetailsManager(){
        //setup the usage of dataSource for userDetailsManager
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);

        return jdbcUserDetailsManager;
    }


}
