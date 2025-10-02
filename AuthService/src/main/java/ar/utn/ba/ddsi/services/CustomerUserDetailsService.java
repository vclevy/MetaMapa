package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomerUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByNombreDeUsuario(nombreDeUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreDeUsuario));

        return User.builder()
                .username(usuario.getNombreDeUsuario())
                .password(usuario.getContrasenia())
                .roles(usuario.getRol().name())
                .build();
    }


}


