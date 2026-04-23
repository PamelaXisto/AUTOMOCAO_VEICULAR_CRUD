package br.com.fecaf.security;

import br.com.fecaf.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final int EXPIRATION = 1000 * 60 * 60;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("fecaf-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                    .sign(algorithm);

        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generation token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("fecaf-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}