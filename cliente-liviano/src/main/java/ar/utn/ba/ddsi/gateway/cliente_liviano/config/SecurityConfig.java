package ar.utn.ba.ddsi.gateway.cliente_liviano.config;

import ar.utn.ba.ddsi.gateway.cliente_liviano.jwt.JwtAuthenticationFilter;
import ar.utn.ba.ddsi.gateway.cliente_liviano.jwt.TokenDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenDecoder tokenDecoder;

    public SecurityConfig(TokenDecoder tokenDecoder) {
        this.tokenDecoder = tokenDecoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "sesion/debug", "/", "/sesion/registrar", "/sesion/login", "/sesion/registro",
                                "/css/**", "/js/**", "/img/**", "hechos/**", "sobre-nosotros","/colecciones/**", "hechos/{id}/solicitar-eliminacion", "/error/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/sesion/login", "/sesion/registrar", "/api/auth/**", "hechos/crear", "hechos/filtrar", "hechos/{id}/solicitar-eliminacion").permitAll()
                        .requestMatchers("/admin/**", "/admin/solicitudes/**").hasRole("ADMIN")
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/sesion/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/error/403");
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/error/401");
                        })
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenDecoder), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


