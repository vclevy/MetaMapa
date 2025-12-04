package ar.utn.ba.ddsi.gateway.cliente_liviano.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class TokenDecoder {

    private final String secretKey = "claveMuySecretaDe32Caracteres123456"; // misma que en AuthService

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getRol(String token) {
        return (String) getClaims(token).get("rol");
    }
}
