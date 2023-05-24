package com.service.dida.global.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (BaseException exception) {
            setErrorResponse(response, exception);
        }
    }

    public void setErrorResponse(HttpServletResponse res, BaseException exception)
        throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(exception.getStatus().value());
        final Map<String, Object> body = new HashMap<>();
        body.put("code", exception.getErrorCode());
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(res.getOutputStream(), body);
    }
}
