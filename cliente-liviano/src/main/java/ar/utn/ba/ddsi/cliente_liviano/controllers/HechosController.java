package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.config.MultipartInputResource;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDinamicaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFiltroDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFormDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.DinamicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String listar(@ModelAttribute("filtros") HechoFiltroDTO filtros, Model model) {

        List<HechoInputDTO> hechos = agregador.obtenerHechos(); // trae todos

        // Filtrar por fechaDesde
        if (filtros.getFechaDesde() != null && !filtros.getFechaDesde().isEmpty()) {
            LocalDate fechaDesdeLD = LocalDate.parse(filtros.getFechaDesde());
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().toLocalDate().isBefore(fechaDesdeLD))
                    .toList();
        }

        // Filtrar por fechaHasta
        if (filtros.getFechaHasta() != null && !filtros.getFechaHasta().isEmpty()) {
            LocalDate fechaHastaLD = LocalDate.parse(filtros.getFechaHasta());
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().toLocalDate().isAfter(fechaHastaLD))
                    .toList();
        }

        // Filtrar por provincia
        if (filtros.getProvincia() != null && !filtros.getProvincia().isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> h.getLugar() != null &&
                            h.getLugar().getProvincia() != null &&
                            h.getLugar().getProvincia().toLowerCase()
                                    .contains(filtros.getProvincia().toLowerCase()))
                    .toList();
        }

        // Filtrar por categoria
     //  if (filtros.getCategoriaId() != null) {
     //      hechos = hechos.stream()
     //              .filter(h -> h.getCategoriaNombre() != null &&
     //                      h.getCategoriaNombre().equalsIgnoreCase(
     //                              agregador.obtenerCategoriaNombrePorId(filtros.getCategoriaId())
     //                      ))
     //              .toList();
     //  }

     //  // Filtrar por fuente
     //  if (filtros.getFuenteId() != null) {
     //      hechos = hechos.stream()
     //              .filter(h -> h.getContribuyente() != null &&
     //                      h.getContribuyente().getId().equals(filtros.getFuenteId()))
     //              .toList();

        model.addAttribute("hechos", hechos);

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
