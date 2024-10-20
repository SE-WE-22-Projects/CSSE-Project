package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.dto.JWTToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class JwtTokenUtil {
    private final ModelMapper modelMapper;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public JwtTokenUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Generate a new access token for the given user
     * @param user the user to generate the token for
     * @return the generated token
     */
    public String generateAccessToken(AuthUser user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUsername());
        claims.put("username", user.getPerson().getName());
        claims.put("role", user.getAuthorities());

        return Jwts.builder().claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey()).compact();
    }

    /**
     * Parse the given token
     * @param token the token to parse
     * @return the parsed token
     */
    public Optional<JWTToken> parseToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(modelMapper.map(jwt.getPayload(), JWTToken.class));
        } catch (JwtException ex) {
            // TODO: actual logging
            ex.printStackTrace();
        } catch (MappingException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Get the secret key for signing in
     * @return the secret key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
