package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.RegistroDTO;
import ar.utn.ba.ddsi.models.entities.Rol;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.repositories.UsuarioRepository;
import ar.utn.ba.ddsi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO registro) {
        if (usuarioRepository.findByNombreDeUsuario(registro.getNombreDeUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Nombre de usuario ya existe");
        }
        if (usuarioRepository.findByEmail(registro.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email ya registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registro.getNombre());
        usuario.setApellido(registro.getApellido());
        usuario.setNombreDeUsuario(registro.getNombreDeUsuario());
        usuario.setEmail(registro.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(registro.getContrasenia()));
        usuario.setRol(Rol.CONTRIBUYENTE);

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

