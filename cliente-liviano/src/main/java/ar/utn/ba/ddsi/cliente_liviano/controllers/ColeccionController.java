package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionCreateDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coleccion")
public class ColeccionController {

    @Autowired
    private AgregadorService agregador;

    // Mostrar formulario
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coleccionCreateDTO", new ColeccionCreateDTO());
        return "adminNuevaColeccion";
    }

    // Crear colección
    @PostMapping("/crear")
    public String crearColeccion(@ModelAttribute ColeccionCreateDTO request, Model model) {
        try {
            ColeccionDTO creada = agregador.crearColeccion(
                    request.getTitulo(),
                    request.getDescripcion(),
                    request.getAlgoritmo()
            );

            model.addAttribute("mensajeExito", "Colección creada con éxito: " + creada.getTitulo());
            model.addAttribute("coleccionCreateDTO", new ColeccionCreateDTO()); // reiniciar formulario

        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al crear la colección: " + e.getMessage());
            model.addAttribute("coleccionCreateDTO", request); // mantener datos ingresados
        }

        return "adminNuevaColeccion";
    }
}
