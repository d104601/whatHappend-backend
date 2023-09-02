package com.whathappened.whathappendbackend.config;

import com.whathappened.whathappendbackend.security.JwtTokenProvider;
import com.whathappened.whathappendbackend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    {
        logger.info("doFilterInternal");
        try {
            String endpoint = request.getRequestURI();
            logger.info("endpoint: " + endpoint);
            if(
                    endpoint.equals("/api/user/login") ||
                    endpoint.equals("/api/user/register") ||
                    endpoint.equals("/api/news/search") ||
                    endpoint.equals("/api/news/trend") ||
                    endpoint.equals("/api/news/category") ||
                    endpoint.startsWith("/api/weather")) {
                logger.info("endpoint: " + endpoint + " is public");
                filterChain.doFilter(request, response);
            }
            else
            {
                logger.info("endpoint: " + endpoint + " is not public");

                String jwt = parseJwt(request);
                logger.info("jwt: " + jwt);
                if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                    String username = jwtTokenProvider.getUsernameFromToken(jwt);

                    logger.info("username: " + username);
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    logger.info("userDetails: " + userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // if the token is valid, set the authentication in context
                    // show the log in console
                    logger.info("authenticationToken: " + authenticationToken.getPrincipal().toString());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: ", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        Optional<String> Auth = Optional.ofNullable(request.getHeader("Authorization"));
        Auth.ifPresent(s -> logger.info("Auth: " + s));
        if(Auth.isPresent() && Auth.get().startsWith("Bearer ")) {
            return Auth.get().substring(7);
        }
        return null;
    }
}
