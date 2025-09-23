package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionCreateDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.EstaticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AgregadorService agregador;
    @Autowired
    private EstaticaService estatica;

    @GetMapping("/")
    public String admin(Model model) {
        // TODO Por ahora los datos del dashboard están HARDCODEADOS en la vista.
        // Más adelante los agregamos dinámicamente con model.addAttribute(...)
        return "adminLanding";
    }

    // Mostrar formulario
    @GetMapping("/coleccion/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coleccionCreateDTO", new ColeccionCreateDTO());
        return "adminNuevaColeccion";
    }

    // Crear colección
    @PostMapping("/coleccion/crear")
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

    @GetMapping("/importar")
    public String showImportPage() {
        return "adminImportar";
    }


    @PostMapping("/importar")
    public ResponseEntity<String> importarArchivo(@RequestParam("archivos") MultipartFile file) {
        try {
            String resultado = estatica.importarArchivo(file).block();
            return ResponseEntity.ok("Archivo importado correctamente: " + resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar archivo: " + e.getMessage());
        }
    }




}
