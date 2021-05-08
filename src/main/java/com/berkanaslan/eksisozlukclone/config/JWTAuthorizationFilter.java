package com.berkanaslan.eksisozlukclone.config;

import com.berkanaslan.eksisozlukclone.model.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected String getToken(HttpServletRequest request) {
        String jwtToken = request.getParameter(ConfigurationConstants.TOKEN_PARAMETER);
        String authorizationHeader = request.getHeader(ConfigurationConstants.AUTHORIZATION_HEADER);

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(ConfigurationConstants.TOKEN_PREFIX)) {
            return authorizationHeader.replace(ConfigurationConstants.TOKEN_PREFIX, "");
        } else if (Objects.nonNull(jwtToken)) {
            return jwtToken;
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getToken(request);
        if (Objects.isNull(token)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(ConfigurationConstants.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            LOGGER.info("JWT expired.");
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        Principal.parseFromJWTString(claims.getSubject()),
                        null,
                        new ArrayList<>()
                )
        );

        chain.doFilter(request, response);
    }
}
