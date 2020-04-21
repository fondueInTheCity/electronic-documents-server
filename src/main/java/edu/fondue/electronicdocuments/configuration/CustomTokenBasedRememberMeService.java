package edu.fondue.electronicdocuments.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

public class CustomTokenBasedRememberMeService extends TokenBasedRememberMeServices {

    private final String HEADER_SECURITY_TOKEN = "Header name for token goes here";

    public CustomTokenBasedRememberMeService(final String key, final UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected String extractRememberMeCookie(final HttpServletRequest request) {
        final String token = request.getHeader(HEADER_SECURITY_TOKEN);
        return token == null || token.length() == 0
                ? null
                : token;
    }
}
