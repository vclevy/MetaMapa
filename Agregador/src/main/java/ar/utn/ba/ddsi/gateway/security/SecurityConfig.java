package ar.utn.ba.ddsi.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/coleccion/**").permitAll()
                        .requestMatchers("/graphiql/**", "/graphql").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/hecho/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hecho/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/hecho/filtrar").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/solicitud/{idSolicitud}/aprobar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/solicitud/{idSolicitud}/rechazar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/solicitud").permitAll()
                        .requestMatchers(HttpMethod.GET, "/graphiql").permitAll()
                        .requestMatchers(HttpMethod.GET, "/graphiql/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/graphql").permitAll()
                        .requestMatchers(HttpMethod.POST, "/graphql").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/solicitud/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers("/actuator/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }
}

