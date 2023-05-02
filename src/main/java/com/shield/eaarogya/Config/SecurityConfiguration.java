package com.shield.eaarogya.Config;


import com.shield.eaarogya.Filter.JwtFilter;
import com.shield.eaarogya.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final UserService userService;

    private final JwtFilter jwtFilter;

    private final JwtEntryPoint jwtEntryPoint;

    public SecurityConfiguration(UserService userService, JwtFilter jwtFilter, JwtEntryPoint jwtEntryPoint) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // After merged with front end, remove .antmachers("/**").permitall() to make security work fine

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
                .antMatchers("/OnlineDoctors/totalOnline")
                .permitAll()
                .antMatchers("/consultation/getAllConsultationsCount/**")
                .permitAll()
//                .antMatchers("/prescription/getPrescriptions/**")
//                .permitAll()
//                .antMatchers("/fileaws/getAllFiles/**")
//                .permitAll()
                .antMatchers("/authenticate/**")
                .permitAll()
                .antMatchers("/login/**")
                .permitAll()
                .antMatchers("/department/**")
                .permitAll()
                .antMatchers("/doctor/registerDoctor/**")
                .permitAll()
                .antMatchers("/patient/addPatient/**")
                .permitAll()
//                .antMatchers("/doctor/getDoctorByPhoneNumber/**")
//                .permitAll()
//                .antMatchers("/patient/getPatientByPhoneNumber/**")
//                .permitAll()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Collections.singletonList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

