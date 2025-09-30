package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.RegistroForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sesion")
public class SesionController {

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "inicioSesion";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute RegistroForm form, Model model) {
        // Llamada a AuthService via WebClient o RestTemplate
        return "login"; // redirigir tras registro
    }

}
