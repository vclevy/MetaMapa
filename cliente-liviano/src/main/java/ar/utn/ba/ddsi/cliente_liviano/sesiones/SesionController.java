package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.LoginDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.RegistroForm;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.TokenProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/sesion")
public class SesionController {

    @Autowired
    private TokenProvider tokenProvider;
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
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session) {

        // Llamada al servicio de autenticación
        AuthResponse tokens = authService.login(username, password);

        if (tokens == null) {
            // Si falla login, redirigir a login con error
            return "redirect:/login?error=true";
        }

        // Guardar tokens en sesión stateful
        session.setAttribute("ACCESS_TOKEN", tokens.getAccessToken());
        session.setAttribute("REFRESH_TOKEN", tokens.getRefreshToken());

        // Redirigir al dashboard / alumnos
        return "redirect:/alumnos";
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
