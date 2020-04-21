package edu.fondue.electronicdocuments.configuration.filters;

import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.services.UserDetailsServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwt = getJwt(httpServletRequest);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                final String username = tokenProvider.getUserNameFromJwtToken(jwt);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Can not set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwt(final HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer", "");
        }

        return null;
    }
}
