package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.config.MultipartInputResource;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFormDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@Controller
@RequestMapping("/hechos")
public class HechosController {

    private final AgregadorService agregador;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public HechosController(AgregadorService agregador,
                            WebClient.Builder builder,
                            ObjectMapper objectMapper) {
        this.agregador = agregador;
        this.webClient = builder.baseUrl("http://localhost:8081")
                .build();
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

    @PostMapping("/crear")
    public String crearHecho(@ModelAttribute("hecho") HechoFormDTO hechoForm,
                             @RequestParam("multimedia") MultipartFile[] archivos,
                             RedirectAttributes redirectAttributes) {
        try {
            String hechoJson = objectMapper.writeValueAsString(hechoForm);

            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("hecho", hechoJson);

            if (archivos != null) {
                for (MultipartFile archivo : archivos) {
                    if (!archivo.isEmpty()) {
                        formData.add("archivos", new MultipartInputResource(archivo));
                    }
                }
            }

            webClient.post()
                    .uri("/hechos") // endpoint dinámico
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(formData))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            redirectAttributes.addFlashAttribute("mensaje", "Hecho creado con éxito.");
            return "redirect:/hechos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el hecho: " + e.getMessage());
            return "redirect:/hechos/subir";
        }
    }

    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        model.addAttribute("hecho", new HechoFormDTO());
        return "subirHecho";
    }
}
