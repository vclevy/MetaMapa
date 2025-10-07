package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.DinamicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/hechos")
public class HechosController {

    private final AgregadorService agregador;
    private final DinamicaService dinamica;
    private static final int PAGE_SIZE = 12;

    @Autowired
    public HechosController(AgregadorService agregador, DinamicaService dinamica) {
        this.agregador = agregador;
        this.dinamica = dinamica;
    }

    @GetMapping
    public String listar(@ModelAttribute("filtros") HechoFiltroDTO filtros,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        List<HechoDTO> hechos = agregador.obtenerHechos(); // trae todos
        List<CategoriaDTO> categorias = agregador.obtenerCategorias();
        model.addAttribute("categorias", categorias);

        int totalHechos = hechos.size();

        if (totalHechos == 0) {
            model.addAttribute("hechos", List.of());
            model.addAttribute("paginaActual", 1);
            model.addAttribute("paginaAnterior", 1);
            model.addAttribute("paginaSiguiente", 1);
            model.addAttribute("totalPaginas", 1);
            return "listadoHechos";
        }

        int totalPaginas = (int) Math.ceil((double) totalHechos / PAGE_SIZE);

        if (page < 1) page = 1;
        if (page > totalPaginas) page = totalPaginas;

        int fromIndex = (page - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, totalHechos);
        List<HechoDTO> hechosPaginados = hechos.subList(fromIndex, toIndex);

        model.addAttribute("hechos", hechosPaginados);
        model.addAttribute("paginaActual", page);
        model.addAttribute("paginaAnterior", page > 1 ? page - 1 : 1);
        model.addAttribute("paginaSiguiente", page < totalPaginas ? page + 1 : totalPaginas);
        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoHechos";
    }

    @PostMapping("/filtrar")
    @ResponseBody
    public List<HechoDTO> filtrar(@RequestBody HechoFiltroDTO filtro) {
        // Llamada al agregador pasando los filtros
        return agregador.filtrarHechos(filtro);
    }

    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hecho", new HechoFormDTO());
        return "subirHecho";
    }

    @PostMapping("/crear")
    public String crearHecho(@Valid @ModelAttribute HechoFormDTO hechoForm,
                             @RequestParam("multimedia") List<MultipartFile> archivos,
                             Authentication auth,
                             RedirectAttributes redirectAttributes) {

        try {
            String username = null;
            if (auth != null && auth.isAuthenticated()) {
                username = auth.getName();
            }
            hechoForm.setNombreDeUsuario(username);

            dinamica.crearHecho(hechoForm, archivos);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Hecho subido correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al subir el hecho: " + e.getMessage());
        }

        return "redirect:/hechos/subir";
    }


    @GetMapping("/all")
    @ResponseBody
    public List<HechoDTO> obtenerTodosHechos() {
        return agregador.obtenerHechos(); // Método que devuelve todos los hechos
    }


    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        HechoDTO hecho = agregador.obtenerHechoPorId(id);

        // Formateamos la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaFormateada = hecho.getFechaDeAcontecimiento().format(formatter);

        model.addAttribute("hecho", hecho);
        model.addAttribute("fechaFormateada", fechaFormateada);

        return "detalleHecho";
    }

    @GetMapping("/{id}/solicitar-eliminacion")
    public String mostrarFormularioSolicitud(Model model) {
        model.addAttribute("solicitud", new SolicitudDTO());
        return "solicitarEliminacion";
    }

}
