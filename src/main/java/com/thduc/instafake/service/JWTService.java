package com.thduc.instafake.service;


import com.thduc.instafake.constant.AuthConstant;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.exception.JWTException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {

    private UserService userService;
    public JWTService(UserService UserService){
        this.userService = UserService;
    }
    public String generateTokenLogin(User User) {

        final String authorities = userService.getAuthority(User).stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String token = "";
        token =  Jwts.builder()
                .setSubject(User.getUsername())
                .claim(AuthConstant.AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, AuthConstant.SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AuthConstant.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
        User User1 = userService.findByUsername(User.getUsername());
        return token;
    }

    private Claims getAllClaimsFromToken(String token) {
                return Jwts.parser()
                        .setSigningKey(AuthConstant.SIGNING_KEY)
                        .parseClaimsJws(token)
                        .getBody();

    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + AuthConstant.EXPIRE_TIME);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private byte[] generateShareSecret() {
        // Generate 256-bit (32-byte) shared secret
        byte[] sharedSecret = new byte[32];
        sharedSecret = AuthConstant.SECRET_KEY.getBytes();
        return sharedSecret;
    }
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateTokenLogin(String token) {
        if (token == null || token.trim().length() == 0) {
            return false;
        }

        String username = getUsernameFromToken(token);
        User user = this.userService.findByUsername(username);
        if (username == null || username.isEmpty()) {
            return false;
        }
        else if (isTokenExpired(token)) {
            throw new JWTException(HttpStatus.BAD_REQUEST, "EXPIRED_TOKEN");
        }
//        else if (user == null || !tokenService.exitsByValueAndUserId(token, user.getId())){
//            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
//            return  false;
//        }
        else {

            return true;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(AuthConstant.SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AuthConstant.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String getRole(String token){
        String user = this.getUsernameFromToken(token);
        User user1 = userService.findByUsername(user);
        UserDetails userDetail =  UserPrinciple.build(user1);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                null, userDetail.getAuthorities());
        return userDetail.getAuthorities().toString();
    }

}
