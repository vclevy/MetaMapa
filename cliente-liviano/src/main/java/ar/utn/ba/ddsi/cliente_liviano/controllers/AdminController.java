package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.jwt.TokenDecoder;
import ar.utn.ba.ddsi.cliente_liviano.models.ResultadoEstadisticaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionCreateDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.FuenteCreateDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.EstadisticasService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.EstaticaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AgregadorService agregador;
    @Autowired
    private EstaticaService estatica;
    @Autowired
    private EstadisticasService estadisticas;
    @Autowired
    private TokenDecoder tokenDecoder;

    private boolean esAdmin(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if (token == null) return false;

        try {
            String rol = tokenDecoder.getRol(token);
            System.out.println("ROL desde token: " + rol); // <-- debug
            return "ADMIN".equalsIgnoreCase(rol);
        } catch (Exception e) {
            e.printStackTrace(); // para ver errores de parsing
            return false;
        }
    }


    @GetMapping("/admin")
    public String adminLanding(Model model, HttpServletRequest request) {
        if (!esAdmin(request)) {
            return "redirect:/sesion/login"; // o página 403
        }

        List<ResultadoEstadisticaDTO> resultados = estadisticas.obtenerTodas();
        model.addAttribute("resultados", resultados);
        return "adminLanding";
    }


    @GetMapping("/coleccion/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coleccionCreateDTO", new ColeccionCreateDTO());
        return "adminNuevaColeccion";
    }

    @PostMapping("/coleccion/crear")
    public String crearColeccion(@ModelAttribute ColeccionCreateDTO request, Model model) {
        try {
            ColeccionDTO creada = agregador.crearColeccion(
                    request.getTitulo(),
                    request.getDescripcion(),
                    request.getAlgoritmo()
            );

            model.addAttribute("mensajeExito", "Colección creada con éxito: " + creada.getTitulo());
            model.addAttribute("coleccionCreateDTO", new ColeccionCreateDTO());

        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al crear la colección: " + e.getMessage());
            model.addAttribute("coleccionCreateDTO", request);
        }

        return "adminNuevaColeccion";
    }

    @GetMapping("/importar")
    public String showImportPage() {
        return "adminImportar";
    }


    @PostMapping("/importar")
    public ResponseEntity<String> importarArchivo(@RequestParam("archivos") MultipartFile[] files) {
        try {
            String resultado = estatica.importarArchivos(files).block();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar archivos: " + e.getMessage());
        }
    }

    @GetMapping("/colecciones")
    public String listarColecciones(Model model) {
        // Obtener todas las colecciones
        List<ColeccionDTO> colecciones = agregador.obtenerColecciones();

        // Inicializar listas vacías para evitar nulls en Thymeleaf
        if (colecciones != null) {
            colecciones.forEach(c -> {
                if (c.getHechosDeLaColeccion() == null) {
                    c.setHechosDeLaColeccion(Collections.emptyList());
                }
            });
        }

        // Pasar la lista al modelo
        model.addAttribute("colecciones", colecciones);

        // Retornar el template Thymeleaf
        return "adminColecciones"; // nombre del archivo HTML sin .html
    }

//    @GetMapping("/solicitudes")
//    public String listarSolicitudes(Model model) {
//        List<Solicitud> = agregador.obtenerSolicitudes();
//    }
    @DeleteMapping("/colecciones/borrar")
    @ResponseBody  // importante para que devuelva algo que el fetch pueda interpretar
    public ResponseEntity<String> borrarColeccion(@RequestParam Long id) {
        boolean eliminado = agregador.eliminarColeccion(id); // llama al backend real
        if (eliminado) {
            return ResponseEntity.ok("Colección eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la colección con id: " + id);
        }
    }

    @GetMapping("/coleccion/editar/{id}")
    public String editarColeccion(@PathVariable Long id, Model model) {
        ColeccionDTO coleccion = agregador.obtenerColeccionPorId(id); // tu servicio
        model.addAttribute("coleccion", coleccion);
        return "adminNuevaColeccion"; // Thymeleaf template editarColeccion.html
    }

    @PostMapping("/guardar")
    public String guardarColeccion(@ModelAttribute ColeccionDTO coleccionModificada) {
        ColeccionDTO coleccionOriginal = agregador.obtenerColeccionPorId(coleccionModificada.getId());

        if (coleccionOriginal != null) {
            agregador.saveOrUpdate(coleccionOriginal, coleccionModificada);
        } else {
            agregador.saveOrUpdate(coleccionOriginal,coleccionModificada); // nueva colección
        }

        return "redirect:/colecciones";
    }

    @PostMapping("/coleccion/{id}/fuente/agregar")
    public String agregarFuentesForm(@PathVariable Long id,
                                     @RequestParam String nombre,
                                     @RequestParam String tipo,
                                     @RequestParam String urlBase,
                                     RedirectAttributes redirectAttrs) {

        FuenteCreateDTO dto = new FuenteCreateDTO();
        dto
                .setNombre(nombre);
        dto.setTipo(tipo);
        dto.setUrlBase(urlBase);

        try {
            agregador.agregarFuentes(id, dto);
            redirectAttrs.addFlashAttribute("mensajeExito", "Fuente agregada correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al agregar fuente: " + e.getMessage());
        }

        return "redirect:/admin/colecciones";
    }

    @PostMapping("/coleccion/{idColeccion}/fuente/eliminar")
    public String eliminarFuenteForm(@PathVariable Long idColeccion,
                                     @RequestParam Long idFuente,
                                     RedirectAttributes redirectAttrs) {
        try {
            agregador.eliminarFuentes(idColeccion, idFuente);
            redirectAttrs.addFlashAttribute("mensajeExito", "Fuente eliminada correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar fuente: " + e.getMessage());
        }
        return "redirect:/admin/colecciones";
    }
}
