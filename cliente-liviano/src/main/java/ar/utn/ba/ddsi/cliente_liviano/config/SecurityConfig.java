package ar.utn.ba.ddsi.cliente_liviano.config;

import ar.utn.ba.ddsi.cliente_liviano.jwt.JwtAuthenticationFilter;
import ar.utn.ba.ddsi.cliente_liviano.jwt.TokenDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenDecoder tokenDecoder;

    public SecurityConfig(TokenDecoder tokenDecoder) {
        this.tokenDecoder = tokenDecoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // públicos (GET)
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/sesion/debug",
                                "/sesion/registro",
                                "/sesion/login",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/uploads/**",
                                "/sobre-nosotros",
                                "/colecciones/**",
                                // listado y detalle de hechos son públicos
                                "/hechos",
                                "/hechos/all",
                                "/hechos/*"          // /hechos/{id}
                        ).permitAll()

                        // públicos (POST)
                        .requestMatchers(HttpMethod.POST,
                                "/sesion/login",
                                "/sesion/registrar",
                                "/api/auth/**",
                                "/hechos/crear"      // subir hecho sin estar logueado (si así lo querés)
                        ).permitAll()

                        // solo autenticados (edición de hecho)
                        .requestMatchers(HttpMethod.GET,  "/hechos/*/editar").authenticated()
                        .requestMatchers(HttpMethod.POST, "/hechos/*/editar").authenticated()

                        // admin
                        .requestMatchers("/admin/**", "/admin/solicitudes/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/sesion/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenDecoder),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


