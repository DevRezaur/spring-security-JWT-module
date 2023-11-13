package com.devrezaur.main.config;

import com.devrezaur.main.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtRequestFilter is a component for handling JWT authentication in the Spring Security filter chain.
 * It extracts the JWT token from the Authorization header, validates it, and sets up the authentication
 * details in the SecurityContextHolder if the token is valid.
 *
 * @author Rezaur Rahman
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private static final String AUTHORIZATION = "Authorization ";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;


    /**
     * Constructor for JwtRequestFilter class.
     *
     * @param jwtUtils the instance of utility class for JWT operations.
     */
    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Performs the actual JWT authentication logic. It extracts the JWT token from the Authorization header,
     * validates it, and sets up the authentication details in the SecurityContextHolder if the token is valid.
     *
     * @param request     the incoming HttpServletRequest.
     * @param response    the outgoing HttpServletResponse.
     * @param filterChain the filter chain for processing the request.
     * @throws ServletException if a servlet exception occurs.
     * @throws IOException      if an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.extractUsername(jwt);
                List<SimpleGrantedAuthority> grantedAuthorities =
                        jwtUtils.extractAuthorities(jwt)
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();
                UserDetails userDetails = new User(username, null, grantedAuthorities);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception e) {
            logger.error("JWT authentication failed!");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Parses the JWT token from the Authorization header.
     *
     * @param request the incoming HttpServletRequest.
     * @return the extracted JWT token or null if not found.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
