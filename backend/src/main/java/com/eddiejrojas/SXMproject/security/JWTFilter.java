package com.eddiejrojas.SXMproject.security;

import com.eddiejrojas.SXMproject.users.models.User;
import com.eddiejrojas.SXMproject.users.services.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtils.getUsernameFromToken(token);
        }

        /*
         * If we have a valid username from our token and we have no authentication, we
         * will provide
         * the user with an authenticated token and allow the user to access protected
         * routes.
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
