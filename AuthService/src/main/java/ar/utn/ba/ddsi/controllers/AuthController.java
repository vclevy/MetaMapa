package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.AuthRequest;
import ar.utn.ba.ddsi.models.dtos.AuthResponse;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    public AuthController(JwtService jwtService,
                          AuthenticationManager authManager,
                          org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario user = (Usuario) authentication.getPrincipal(); // ahora tu entidad/implementación UserDetails
        String accessToken = jwtService.generateAccessToken((UserDetails) user);
        String refreshToken = jwtService.generateRefreshToken((UserDetails) user);

        return ResponseEntity.ok(
                new AuthResponse(accessToken, refreshToken,
                        ((UserDetails) user).getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).toList())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        String newAccess = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(
                new AuthResponse(newAccess, refreshToken,
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).toList())
        );
    }
}
