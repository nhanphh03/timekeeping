package com.lottefn.collateral.domain.configs.security;

import com.lottefn.collateral.domain.entities.Role;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.entities.data.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.expiration}")
    public long JWT_EXPIRATION;

    @Value("${jwt.secret}")
    public String SECRET_KEY;

    private final ModelMapper modelMapper;

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION * 3600 * 1000);
        return Jwts.builder()
                .header()  // Set header (new way in 0.12.x)
                .add("typ", "JWT")
                .and()
                .claims(getClaims(userDetails.getUser()))  // Set claims
                .subject(Long.toString(userDetails.getUser().getId()))
                .issuedAt(now)
                .expiration(expiryDate)  // Non-deprecated method
                .signWith(getSigningKey())
                .compact();

    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    public User getUserFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return User.builder()
                .id(Long.parseLong(claims.getSubject()))
                .name((String) claims.get("name"))
                .email((String) claims.get("email"))
                .username((String) claims.get("username"))
                .role(modelMapper.map(claims.get("role"), Role.class))
                .build();
    }

    public boolean validateToken(String authToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);

            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (JwtException  ex) {
            log.error("JWT signature does not match");
        }
        return false;
    }

    private Map<String, Object> getClaims(User user){
        Map<String, Object> mClaims = new HashMap<>();
        mClaims.put("email", user.getEmail());
        mClaims.put("name", user.getName());
        mClaims.put("username", user.getUsername());
        mClaims.put("role", user.getRole());
        return mClaims;
    }

    private Map<String, Object> header(){
        Map<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        return map;
    }

    private  SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
