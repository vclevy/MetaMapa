package ar.utn.ba.ddsi.cliente_liviano.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenDecoder tokenDecoder;

    public JwtAuthenticationFilter(TokenDecoder tokenDecoder) {
        this.tokenDecoder = tokenDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        String token = (String) request.getSession().getAttribute("token");
        if (token != null) {
            try {
                String rol = tokenDecoder.getRol(token);
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        tokenDecoder.getUsername(token), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
