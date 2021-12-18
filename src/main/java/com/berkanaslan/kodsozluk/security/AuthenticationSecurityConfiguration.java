package com.berkanaslan.kodsozluk.security;

import com.berkanaslan.kodsozluk.config.ConfigurationConstants;
import com.berkanaslan.kodsozluk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.stream.Stream;

@Configuration
@Order(1)
public class AuthenticationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher(ConfigurationConstants.LOGIN_URL)
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, ConfigurationConstants.LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter(authenticationManager()));

        http.headers().frameOptions().disable();
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager, userService);
        jwtAuthenticationFilter.setFilterProcessesUrl(ConfigurationConstants.LOGIN_URL);
        return jwtAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        Stream.of(HttpMethod.values()).forEach(m -> corsConfiguration.addAllowedMethod(m.name()));

        corsConfiguration.addExposedHeader(ConfigurationConstants.TOKEN_HEADER);
        corsConfiguration.addExposedHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
