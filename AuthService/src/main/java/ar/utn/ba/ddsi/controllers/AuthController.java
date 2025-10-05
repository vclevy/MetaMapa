package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.LoginDTO;
import ar.utn.ba.ddsi.models.dtos.RegistroDTO;
import ar.utn.ba.ddsi.models.entities.LoginResponse;
import ar.utn.ba.ddsi.models.entities.Rol;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.repositories.UsuarioRepository;
import ar.utn.ba.ddsi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

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
        usuario.setContrasenia(passwordEncoder.encode(registro.getClave()));
        usuario.setRol(Rol.ADMIN);

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        // 1. Buscar usuario por username
        Optional<Usuario> userOpt = usuarioRepository.findByNombreDeUsuario(request.getNombreDeUsuario());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Usuario usuario = userOpt.get();

        // 2. Verificar contraseña
        if (!passwordEncoder.matches(request.getClave(), usuario.getContrasenia())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // 3. Generar token JWT
        String token = jwtService.generateToken(usuario);

        // 4. Devolver token
        return ResponseEntity.ok(new LoginResponse(token));
    }

}