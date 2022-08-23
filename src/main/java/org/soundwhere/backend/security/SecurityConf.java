package org.soundwhere.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Elements.REMEMBER_ME;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    private final DatabaseDetailService databaseDetailService;
    private final RestAuthEntryPoint restAuthEntryPoint;

    public SecurityConf(DatabaseDetailService databaseDetailService, RestAuthEntryPoint restAuthEntryPoint) {
        this.databaseDetailService = databaseDetailService;
        this.restAuthEntryPoint = restAuthEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/rest/users/").permitAll()
            .requestMatchers(AnyRequestMatcher.INSTANCE).authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(restAuthEntryPoint)
            .and()
            .rememberMe()
            .rememberMeParameter(REMEMBER_ME)
            .tokenValiditySeconds(60 * 60 * 24 * 30);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring().antMatchers("/rest/errors/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(databaseDetailService);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(8);
    }

}
