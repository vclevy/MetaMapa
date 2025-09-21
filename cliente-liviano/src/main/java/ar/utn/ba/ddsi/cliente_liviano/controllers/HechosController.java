package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.config.MultipartInputResource;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDinamicaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFormDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.DinamicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/hechos")
public class HechosController {

    private final AgregadorService agregador;
    private final DinamicaService dinamica;
    private final ObjectMapper objectMapper;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public HechosController(AgregadorService agregador, DinamicaService dinamica,
                            ObjectMapper objectMapper) {
        this.agregador = agregador;
        this.dinamica = dinamica;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String fuente,
            Model model) {

        List<HechoInputDTO> hechos = agregador.obtenerHechos(); // trae todos

        if (fechaDesde != null && !fechaDesde.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().isBefore(LocalDate.parse(fechaDesde).atStartOfDay()))
                    .toList();
        }
        if (fechaHasta != null && !fechaHasta.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().isAfter(LocalDate.parse(fechaHasta).atStartOfDay()))
                    .toList();
        }
        if (ubicacion != null && !ubicacion.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> h.getLugar().getProvincia().toLowerCase().contains(ubicacion.toLowerCase()))
                    .toList();
        }
        if (categoria != null && !categoria.isEmpty() && !categoria.equalsIgnoreCase("todas")) {
            hechos = hechos.stream()
                    .filter(h -> h.getCategoriaNombre().equalsIgnoreCase(categoria))
                    .toList();
        }

        model.addAttribute("hechos", hechos);
        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("categoria", categoria);
        model.addAttribute("fuente", fuente);

        // Paginación
        int totalHechos = hechos.size();
        int totalPaginas = (int) Math.ceil((double) totalHechos / PAGE_SIZE);
        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoHechos";
    }

    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hecho", new HechoFormDTO());
        return "subirHecho";
    }

    @PostMapping("/crear")
    public String crearHecho( @Valid @ModelAttribute HechoFormDTO hechoForm,
                             @RequestParam("multimedia") List<MultipartFile> archivos,
                             RedirectAttributes redirectAttributes) {

        try {
            HechoDinamicaDTO hecho = dinamica.crearHecho(hechoForm, archivos);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Hecho subido correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al subir el hecho: " + e.getMessage());
        }

        return "redirect:/hechos/subir";
    }


    @GetMapping("/all")
    @ResponseBody
    public List<HechoInputDTO> obtenerTodosHechos() {
        return agregador.obtenerHechos(); // Método que devuelve todos los hechos
    }

    @GetMapping("/bounds")
    public List<HechoInputDTO> obtenerHechosPorBounds(
            @RequestParam double south,
            @RequestParam double west,
            @RequestParam double north,
            @RequestParam double east) {

        return agregador.obtenerHechosEnBounds(south, west, north, east);
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        HechoInputDTO hecho = agregador.obtenerHechoPorId(id);

        // Formateamos la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaFormateada = hecho.getFechaDeAcontecimiento().format(formatter);

        model.addAttribute("hecho", hecho);
        model.addAttribute("fechaFormateada", fechaFormateada);

        return "detalleHecho";
    }

}
