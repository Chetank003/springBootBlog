package blog.security;

import blog.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTAuthenticationUtil {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String tokenSecret;
    @Value("${jwt.expiresIn}")
    private long expiresIn;

    public boolean isUserCredentialsValid(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String createToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

}
