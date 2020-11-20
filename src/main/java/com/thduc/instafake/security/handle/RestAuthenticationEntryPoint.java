package com.thduc.instafake.security.handle;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
//     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("Unauthorized");
        final String expiredMsg = (String) request.getAttribute("token");
        final String msg = (expiredMsg != null) ? expiredMsg : "Your login session has expired";
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
    }
}
