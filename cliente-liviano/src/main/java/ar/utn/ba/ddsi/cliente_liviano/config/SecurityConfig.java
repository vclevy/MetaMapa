package ar.utn.ba.ddsi.cliente_liviano.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // GET páginas públicas
                        .requestMatchers(HttpMethod.GET,
                                "/", "/sesion/login", "/sesion/registro",
                                "/css/**", "/js/**", "/img/**"
                        ).permitAll()
                        // POST login y registro (se manda al AuthService)
                        .requestMatchers(HttpMethod.POST,
                                "/sesion/login", "/sesion/registrar", "/api/auth/**"
                        ).permitAll()
                        // /admin/** solo para ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // cualquier otra requiere auth
                        .anyRequest().authenticated()
                )
                // insertar filtro JWT antes de que se procese UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // logout
                .logout(logout -> logout
                        .logoutUrl("/sesion/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
