package com.thduc.instafake.filter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


import com.thduc.instafake.constant.AuthConstant;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.service.JWTService;
import com.thduc.instafake.service.UserService;
import com.thduc.instafake.service.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
//    private TokenServiceImpl tokenService;

    public JwtAuthenticationTokenFilter(UserService UserService){
        this.userService = UserService;
//        this.tokenService = tokenService;
    }

    public JwtAuthenticationTokenFilter(){
        super();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authToken;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        authToken = httpRequest.getHeader(AuthConstant.HEADER_STRING);
        try {
            if (jwtService.validateTokenLogin(authToken)) {
                String username = jwtService.getUsernameFromToken(authToken);
                com.thduc.instafake.entity.User user = userService.findByUsername(username);
                if (user.getUsername() != null) {
                    UserDetails userDetail =  UserPrinciple.build(user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                            null, userDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        catch (ExpiredJwtException ex) {
            request.setAttribute("token", "Login session expires");
//            tokenService.deleteByValue(deleteByValueauthToken);
        }
        catch (MalformedJwtException ex){
            request.setAttribute("token", "Invalid token fomat");
        }
        catch (SignatureException ex){
            request.setAttribute("token", "Invalid token");
        }
        catch (NullPointerException ex){
            request.setAttribute("token", "Invalid user, try again");
        }
        chain.doFilter(request, response);
    }
}
