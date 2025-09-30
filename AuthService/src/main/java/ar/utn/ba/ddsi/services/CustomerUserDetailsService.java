package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UsuarioRepository usuarioRepository;
//
//    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
//        this.usuarioRepository = usuarioRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        var usuario = usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
//
//        return User.builder()
//                .username(usuario.getEmail())
//                .password(usuario.getPassword())
//                .authorities(usuario.getRoles()
//                        .stream()
//                        .map(r -> r.getNombre())
//                        .toArray(String[]::new))
//                .build();
//    }
//}
