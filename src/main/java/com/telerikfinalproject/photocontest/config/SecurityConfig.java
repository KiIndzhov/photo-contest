package com.telerikfinalproject.photocontest.config;

import com.telerikfinalproject.photocontest.security.AuthenticationSuccessHandlerImpl;
import com.telerikfinalproject.photocontest.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/junkie", "/**/junkie/**","/**/enroll/**").hasRole("PHOTO_JUNKIE")
                .antMatchers("/organiser", "/**/organiser/**").hasRole("ORGANIZER")
                .antMatchers("/contest/**","/photo/**", "/profile/**","/logoutPage").authenticated()
                .antMatchers("/","**/h2-console/**").permitAll()
                .and().formLogin().loginPage("/login").successHandler( authenticationSuccessHandler )
                .and().exceptionHandling().accessDeniedPage("/")
                .and().logout().logoutSuccessUrl("/").invalidateHttpSession(true);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // change bottom two for encrypting passwords
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }


}
