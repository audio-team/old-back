package org.soundwhere.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    static Logger logger = LoggerFactory.getLogger(RestAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.info("🦀 Request with auth fail found.");
        response.sendRedirect("/rest/errors/unauthenticated");
    }
}
