package com.berkanaslan.eksisozlukclone.config;

import com.berkanaslan.eksisozlukclone.security.AuthenticationSecurityConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * {@link AuthenticationSecurityConfiguration} checks access to all URLs. URLs for password management of users permit them all.
 * This filter for getting localization code information from http header.
 * {@link Order} set the value to -101 so that this filter runs before the security filter chain.
 *
 * @author berkanaslan
 */

@Component
@Order(-101)
public class LocaleHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String localeKey = httpRequest.getHeader("X-Locale-Info");
        if (localeKey != null) {
            LocaleContextHolder.setLocaleKey(localeKey);
        } else {
            LocaleContextHolder.clear();
        }

        chain.doFilter(request, response);
    }
}
