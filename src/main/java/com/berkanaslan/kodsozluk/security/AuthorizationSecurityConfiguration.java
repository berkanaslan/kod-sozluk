package com.berkanaslan.kodsozluk.security;

import com.berkanaslan.kodsozluk.config.ConfigurationConstants;
import com.berkanaslan.kodsozluk.response.ResponseWrapper;
import com.berkanaslan.kodsozluk.util.ExceptionMessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(2)
public class AuthorizationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/**",
                        ConfigurationConstants.ENTRY_URL,
                        ConfigurationConstants.TITLE_URL,
                        "/api-docs/**",
                        "/head/**",
                        "/topic/**",
                        "/entry/**",
                        "/api-docs.html/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(this::handleForbiddenException)
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }

    private void handleForbiddenException(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ResponseWrapper responseWrapper = new ResponseWrapper(ExceptionMessageUtil.getMessageByLocale("message.forbidden"),
                ExceptionMessageUtil.getMessageByLocale("message.forbidden"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(objectMapper.writeValueAsString(responseWrapper));
    }
}

