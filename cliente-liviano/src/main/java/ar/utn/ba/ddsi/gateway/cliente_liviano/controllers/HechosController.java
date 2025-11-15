package ar.utn.ba.ddsi.gateway.cliente_liviano.controllers;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.Lugar;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.DinamicaService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/hechos")
public class HechosController {

    private final AgregadorService agregador;
    private final DinamicaService dinamica;
    static final int PAGE_SIZE = 12;

    @Autowired
    public HechosController(AgregadorService agregador, DinamicaService dinamica) {
        this.agregador = agregador;
        this.dinamica = dinamica;
    }

    @GetMapping
    public String listar(@ModelAttribute("filtros") HechoFiltroDTO filtros,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        List<HechoDTO> hechos = agregador.obtenerHechosVisibles();
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
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("hecho", new HechoFormDTO()); // el que ya usabas en alta
        return "subirHecho"; // <-- vista de ALTA
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

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        HechoDTO hecho = agregador.obtenerHechoPorId(id);

        // Formateo de fecha (si querés mantenerlo)
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaFormateada = hecho.getFechaDeAcontecimiento().format(f);

        // Usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioActual = (auth != null && auth.isAuthenticated()) ? auth.getName() : null;

        // *** AQUÍ se revisa si el usuario actual es el dueño del hecho ***
        boolean puedeEditar =
                usuarioActual != null &&
                        hecho.getNombreDeUsuario() != null &&
                        hecho.getNombreDeUsuario().trim().equalsIgnoreCase(usuarioActual.trim());

        model.addAttribute("hecho", hecho);
        model.addAttribute("fechaFormateada", fechaFormateada);
        model.addAttribute("usuarioActual", usuarioActual);
        model.addAttribute("puedeEditar", puedeEditar);

        return "detalleHecho";
    }

    @GetMapping("/{id}/solicitar-eliminacion")
    public String mostrarFormularioSolicitud(@PathVariable Long id, Model model) {
        SolicitudDTO solicitud = new SolicitudDTO();
        solicitud.setIdHecho(id); // 👈 importante
        model.addAttribute("solicitud", solicitud);
        return "solicitarEliminacion";
    }

    @PostMapping("/{id}/solicitar-eliminacion")
    public String procesarSolicitudEliminacion(
            @PathVariable Long id,
            @ModelAttribute("solicitud") SolicitudDTO solicitudDTO,
            RedirectAttributes redirectAttributes,
            Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioActual = (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonimo";


        solicitudDTO.setNombreDeUsuario(usuarioActual);


        agregador.solicitarEliminacion(solicitudDTO);

        redirectAttributes.addFlashAttribute("mensajeExito", "Solicitud enviada correctamente.");
        return "redirect:/hechos";
    }


    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        HechoDTO hecho = agregador.obtenerHechoPorId(id);
        if (hecho.getLugar() == null) hecho.setLugar(new Lugar());
        model.addAttribute("hecho", hecho);
        return "edicionHecho";
    }

    // Procesar el formulario enviado
    @PostMapping("/{id}/editar")
    public String editarHecho(@PathVariable Long id, @ModelAttribute HechoDTO hechoDTO) {
        hechoDTO.setId(id); // aseguramos que tenga el id
        agregador.editarHecho(hechoDTO);
        return "redirect:/hechos"; //
    }



}
