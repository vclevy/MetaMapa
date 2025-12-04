package ar.utn.ba.ddsi.gateway.cliente_liviano.controllers;

import ar.utn.ba.ddsi.gateway.cliente_liviano.jwt.TokenDecoder;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.LoginDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.RegistroForm;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sesion")
public class SesionController {

    @Autowired
    private TokenDecoder tokenDecoder;
    private final AuthService authService;

    @Autowired
    public SesionController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "inicioSesion";
    }

    @PostMapping("/login")
    public String loginUsuario(@ModelAttribute LoginDTO loginDTO, HttpSession session, Model model) {
        String token = authService.login(loginDTO.getNombreDeUsuario(), loginDTO.getClave());
        if (token != null) {
            session.setAttribute("username", loginDTO.getNombreDeUsuario());
            session.setAttribute("token", token);

            String rol = tokenDecoder.getRol(token);
            session.setAttribute("rol", rol);

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(loginDTO.getNombreDeUsuario(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            Authentication current = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Usuario logueado: " + current.getName());
            System.out.println("Roles: " + current.getAuthorities());
            System.out.println("Token: " + token);

            if ("ADMIN".equalsIgnoreCase(rol)) {
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Usuario o clave incorrectos");
            return "inicioSesion";
        }
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute RegistroForm registroForm, Model model) {
        try {
            Map<String, Object> response = authService.registrar(registroForm);
            return "redirect:/sesion/login";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar: " + e.getMessage());
            return "registro";
        }
    }

    @PostMapping("/logout")
    public String logoutUsuario(HttpSession session) {
        session.invalidate(); // cierra la sesión
        return "redirect:/";  // redirige a la página principal
    }

    @GetMapping("/debug")
    @ResponseBody
    public String debug(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null ? "No auth" : auth.toString();
    }

}
