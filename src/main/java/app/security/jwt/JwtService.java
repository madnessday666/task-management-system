package app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Класс реализующий работу с JWT.
 */
@Service
public class JwtService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.expiresInMs}")
    private long expiresInMs;


    /**
     * Метод для извлечения из токена данных с опредленным ключем.
     *
     * @param jwt       токен.
     * @param claimName ключ, по которому необходимо получить его значение.
     * @return Соответствующее ключу значение ключа типа {@link Object}.
     */
    public Object extractClaim(String jwt, String claimName) {
        return this.extractAllClaims(jwt).get(claimName);
    }

    /**
     * Метод для извлечения из токена именя пользователя.
     *
     * @param jwt токен.
     * @return имя пользователя.
     */
    public String extractUsername(String jwt) {
        return this.extractAllClaims(jwt).getSubject();
    }

    /**
     * Метод для извлечения из токена даты истечения срока действия.
     *
     * @param jwt токен.
     * @return {@link Date} с датой и временем.
     */
    private Date extractExpiration(String jwt) {
        return this.extractAllClaims(jwt).getExpiration();
    }

    public Optional<Object> extractClaimFromHttpServletRequestHeader(String claimName, @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (authHeader != null && authHeader.startsWith("Bearer ")) ?
                Optional.of(this.extractClaim(authHeader.substring(7), claimName))
                : Optional.empty();
    }

    /**
     * Метод для извлечения из токена всех данных.
     *
     * @param jwt токен.
     * @return {@link Claims} с данными.
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(this.getVerifyKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    /**
     * Метод для генерации ключа подтверждения.
     *
     * @return {@link SecretKey}
     */
    private SecretKey getVerifyKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Метод генерации токена с произвольным набором хранимых в нем данных.
     *
     * @param extraClaims произвольные данные для передачи в токен.
     * @param userDetails данные пользователя.
     * @return сгенерированный токен в формате {@link String}
     */
    public String generateToken(Map<String, Object> extraClaims, @NonNull UserDetails userDetails) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.expiresInMs))
                .signWith(getVerifyKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Метод для проверки валидности токена.
     *
     * @param jwt         токен.
     * @param userDetails данные пользователя.
     * @return {@literal true} - если токен валиден, в противном случае - {@literal false}.
     */
    public boolean isTokenValid(String jwt, @NonNull UserDetails userDetails) {
        final String username = this.extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(jwt);
    }

    /**
     * Метод для проверки срока действия токена.
     *
     * @param jwt токен.
     * @return {@literal true} - если срок действия не истек, в противном случае - {@literal false}.
     */
    private boolean isTokenExpired(String jwt) {
        return this.extractExpiration(jwt).before(new Date());
    }

}
