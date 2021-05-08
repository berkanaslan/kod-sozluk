package com.berkanaslan.eksisozlukclone.config;

import com.berkanaslan.eksisozlukclone.model.Principal;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.model.dto.AuthenticationRequest;
import com.berkanaslan.eksisozlukclone.response.ResponseControllerAdvice;
import com.berkanaslan.eksisozlukclone.response.ResponseWrapper;
import com.berkanaslan.eksisozlukclone.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
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

        User user = userService.getUserInformation(principal.getUserId());
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
}
