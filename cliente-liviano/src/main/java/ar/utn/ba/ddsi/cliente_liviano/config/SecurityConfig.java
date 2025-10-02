package ar.utn.ba.ddsi.cliente_liviano.config;

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
                        // GET páginas públicas
                        .requestMatchers(HttpMethod.GET, "sesion/debug", "/",  "/sesion/registrar", "/sesion/login", "/sesion/registro", "/css/**", "/js/**", "/img/**").permitAll()
                        // POST login y registro
                        .requestMatchers(HttpMethod.POST, "/sesion/login", "/sesion/registrar").permitAll()
                        // /admin/** solo ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // el resto requiere auth
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout
                        .logoutUrl("/sesion/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}



