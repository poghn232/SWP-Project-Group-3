package com.example.demo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/";

        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/dashboard";
                break;
            } else if (authority.equals("ROLE_MANAGER")) {
                redirectUrl = "/manager/dashboard";
                break;
            } else if (authority.equals("ROLE_STAFF")) {
                redirectUrl = "/staff/dashboard";
                break;
            } else if (authority.equals("ROLE_SHIPPER")) {
                redirectUrl = "/shipper/dashboard";
                break;
            } else if (authority.equals("ROLE_CUSTOMER")) {
                redirectUrl = "/customer/dashboard";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
