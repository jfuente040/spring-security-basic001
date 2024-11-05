package com.jfuente040.spring_security_basic.security;

import com.jfuente040.spring_security_basic.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        // Check if the Authorization header is null or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No JWT token found in request");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            //If the Authorization header is not null and starts with "Bearer ", extract the JWT token
            final String jwt = authHeader.substring(7);
            // Extract the user name from the JWT token
            final String userName = jwtService.extractUsername(jwt);
            System.out.println("JWT token found in request: " + jwt);
            // Get the authentication from the security context by user name
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // Check if the user name is not null and the authentication is null
            // This means that the user is not authenticated
            // and the SecurityContextHolder does not have an 'authentication token'
            if (userName != null && authentication == null) {
                // Load the user details
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                // Check if the token is valid for the user details
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create an 'authentication token' with the user details and no credentials
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // Set the authentication token with the request details (remote address, session id, etc.)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set the 'authentication token' in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
