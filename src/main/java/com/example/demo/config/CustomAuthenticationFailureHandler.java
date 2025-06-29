// src/main/java/com/example/demo/config/CustomAuthenticationFailureHandler.java
package com.example.demo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Username or password incorrect."; // Thông báo mặc định

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Username not found.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Password incorrect.";
        } else if (exception instanceof LockedException) {
            errorMessage = "Your account has been locked.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Your account hasn't been enabled.";
        }

        // Lấy username đã nhập để điền lại vào form
        String username = request.getParameter("username");

        // Sử dụng FlashMap để truyền dữ liệu qua redirect
        FlashMap flashMap = new FlashMap();
        flashMap.put("loginError", errorMessage); // Đặt thông báo lỗi chi tiết
        flashMap.put("oldUsername", username);   // Đặt username cũ

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        // Chuyển hướng về trang đăng nhập với các flash attributes
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
