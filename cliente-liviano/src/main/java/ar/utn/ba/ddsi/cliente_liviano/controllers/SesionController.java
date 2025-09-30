package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.RegistroForm;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/sesion")
public class SesionController {

    private final AuthService authService;

    @Autowired
    public SesionController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "registro"; // Thymeleaf: registro.html
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "inicioSesion";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute RegistroForm registroForm, Model model) {
        try {
            Map<String, Object> response = authService.registrar(registroForm);
            // Podés mostrar un mensaje de éxito
            model.addAttribute("mensajeExito", "Usuario registrado con éxito. Por favor inicia sesión.");
            return "inicioSesion"; // redirige a login
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar: " + e.getMessage());
            return "registro"; // vuelve al formulario
        }
    }

}
