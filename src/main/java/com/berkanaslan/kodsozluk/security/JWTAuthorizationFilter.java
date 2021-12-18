package com.berkanaslan.kodsozluk.security;

import com.berkanaslan.kodsozluk.config.ConfigurationConstants;
import com.berkanaslan.kodsozluk.model.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
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

        // Refresh the token
        Date date = new Date();
        if (claims.getExpiration().getTime() - date.getTime() < ConfigurationConstants.TOKEN_REFRESH_TIME) {
            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            response.addHeader(ConfigurationConstants.TOKEN_HEADER, JWTAuthenticationFilter.createToken(principal));
            response.setStatus(210);
        }

        chain.doFilter(request, response);
    }
}
