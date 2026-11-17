package com.bank.account;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Logs the exact path the backend receives, proving the gateway rewrote it. */
@Component
public class PathLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(PathLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        log.info(">>> BACKEND RECEIVED: {} {}", req.getMethod(), req.getRequestURI());
        res.setHeader("X-Backend-Received-Path", req.getRequestURI());
        chain.doFilter(req, res);
    }
}
