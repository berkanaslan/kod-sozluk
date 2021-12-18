package com.berkanaslan.kodsozluk.security;

import com.berkanaslan.kodsozluk.config.ConfigurationConstants;
import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.model.core.AuthenticationRequest;
import com.berkanaslan.kodsozluk.response.ResponseControllerAdvice;
import com.berkanaslan.kodsozluk.response.ResponseWrapper;
import com.berkanaslan.kodsozluk.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationRequest authRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException {
        Principal principal = (Principal) auth.getPrincipal();
        String token = createToken(principal);

        response.setContentType("application/json");
        response.addHeader(ConfigurationConstants.TOKEN_HEADER, token);
        response.setStatus(200);

        final User user = userService.getUserInformation(principal.getUserId());
        ResponseWrapper responseWrapper = new ResponseWrapper(ResponseControllerAdvice.SUCCESS_MESSAGE, user);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseWrapper));
    }

    static String createToken(Principal principal) {
        return Jwts.builder()
                .setSubject(principal.getJwtString())
                .setExpiration(new Date(System.currentTimeMillis() + ConfigurationConstants.TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, ConfigurationConstants.SECRET)
                .compact();
    }

    private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            String reasonPhrase = HttpStatus.UNAUTHORIZED.getReasonPhrase();

            if (exception instanceof BadCredentialsException) {
                reasonPhrase = "Bad Credentials: Username or password wrong!";
                response.setHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY, ConfigurationConstants.AUTHENTICATION_ERROR_BAD_CRED);
            } else if (exception instanceof DisabledException) {
                reasonPhrase = "User Disabled!";
                response.setHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY, ConfigurationConstants.AUTHENTICATION_ERROR_DISABLED);
            } else if (exception instanceof LockedException) {
                reasonPhrase = "User Locked!";
                response.setHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY, ConfigurationConstants.AUTHENTICATION_ERROR_LOCKED);
            } else if (exception instanceof InternalAuthenticationServiceException) {
                if (exception.getMessage().isEmpty()) {
                    reasonPhrase = "Authentication Service Got An Error!";
                } else {
                    reasonPhrase = exception.getMessage();
                }

                response.setHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY, ConfigurationConstants.AUTHENTICATION_ERROR_INTERNAL_SERVICE_ERROR);
            } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                if (exception.getMessage().isEmpty()) {
                    reasonPhrase = "Authentication Service Authenticate The User But Oracle Not Recognize This Username or Maybe Oracle Instance Is Down!";
                } else {
                    reasonPhrase = exception.getMessage();
                }

                response.setHeader(ConfigurationConstants.AUTHENTICATION_ERROR_HEADER_KEY, ConfigurationConstants.AUTHENTICATION_ERROR_USER_NOT_FOUND_ERROR);
            }

            response.setStatus(403);
            response.setContentType("application/json");

            ResponseWrapper responseWrapper = new ResponseWrapper("Unauthorized!", reasonPhrase);
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseWrapper));
        }
    }
}


