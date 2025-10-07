    package ar.utn.ba.ddsi.security;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.List;

    @Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final String secretKey = "claveMuySecretaDe32Caracteres123456";

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            System.out.println("Authorization header: " + authHeader);
            System.out.println("Token extraído: " + token);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                String rol = (String) claims.get("rol");

                if (username != null && rol != null) {
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                System.out.println("Error al validar token JWT: " + e.getMessage());
            }

            filterChain.doFilter(request, response);
        }
    }
