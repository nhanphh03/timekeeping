package com.lottefn.collateral.domain.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
            throws IOException {
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> errors = new HashMap<>();
        Clock clock = Clock.systemDefaultZone();
        Instant instant = clock.instant();

        errors.put("timestamp", instant.toString());
        errors.put("message", "Unauthorized!");

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(401);
        res.getWriter().write(oMapper.writeValueAsString(errors));
    }

}
