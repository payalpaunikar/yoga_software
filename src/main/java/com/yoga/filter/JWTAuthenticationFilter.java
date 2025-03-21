package com.yoga.filter;


import com.yoga.component.Role;
import com.yoga.exception.UserNotFoundException;
import com.yoga.service.JWTService;
import com.yoga.service.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

     private JWTService jwtService;

     private MyUserDetailsService myUserDetailsService;

     @Autowired
     public JWTAuthenticationFilter(JWTService jwtService,MyUserDetailsService myUserDetailsService){
         this.jwtService = jwtService;
         this.myUserDetailsService=myUserDetailsService;
     }

     Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //logger.info("Authheader : "+authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           // logger.info("the request is public");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            String emailId = jwtService.extractEmailId(token);

          //  logger.info("The username after token extraction is : " + emailId);

            if (emailId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = myUserDetailsService.loadUserByUsername(emailId);

              //  logger.info("after get userdetails from our application : " + userDetails.getUsername());

                    if (jwtService.validateToken(token, userDetails)) {
                        // logger.info("jwt token is valid");

                        List<Role> roles = jwtService.extractRole(token);

                        List<SimpleGrantedAuthority> authorities = roles
                                .stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                                .toList();

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

            }

            filterChain.doFilter(request, response);
        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid Token\", \"message\": \"User does not exist\"}");

        }
        catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"JWT Token has expired\", \"message\": \"Please Login again, JWT Token expired\"}");
        }
    }
}
