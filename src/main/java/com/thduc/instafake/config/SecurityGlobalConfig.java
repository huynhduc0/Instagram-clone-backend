package com.thduc.instafake.config;

import com.google.common.collect.ImmutableList;
import com.thduc.instafake.filter.JwtAuthenticationTokenFilter;
import com.thduc.instafake.security.handle.CustomAccessDeniedHandler;
import com.thduc.instafake.security.handle.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityGlobalConfig extends WebSecurityConfigurerAdapter {

    private RestAuthenticationEntryPoint unauthorizedHandler;
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }
    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors();
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/google").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/img/*/*").permitAll()
//                .antMatchers("**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler()).authenticationEntryPoint(restServicesEntryPoint());

    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//
////        configuration.setAllowedOrigins(ImmutableList.of("*")); // www - obligatory
//        configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
//        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
}
