package ar.utn.ba.ddsi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/coleccion/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hecho/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/coleccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/coleccion/**").hasRole("ADMIN")

                        // lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }
}

