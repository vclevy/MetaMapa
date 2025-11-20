package ar.utn.ba.ddsi.gateway.cliente_liviano.controllers;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.AgregadorService;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.EstadisticasService;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.EstaticaService;
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

import static ar.utn.ba.ddsi.gateway.cliente_liviano.controllers.HechosController.PAGE_SIZE;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AgregadorService agregador;
    @Autowired
    private EstaticaService estatica;
    @Autowired
    private EstadisticasService estadisticas;


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

        if (colecciones != null) {
            colecciones.forEach(c -> {
                if (c.getHechosDeLaColeccion() == null) {
                    c.setHechosDeLaColeccion(Collections.emptyList());
                }
            });
        }

        model.addAttribute("colecciones", colecciones);

        return "adminColecciones"; // nombre del archivo HTML sin .html
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        agregador.eliminarColeccion(id);
        return "redirect:/admin/colecciones";
    }

    @GetMapping("/coleccion/editar/{id}")
    public String editarColeccion(@PathVariable Long id, Model model) {
        ColeccionDTO coleccion = agregador.obtenerColeccionPorId(id);

        if (coleccion == null) {
            throw new IllegalArgumentException("No existe coleccion con id " + id);
        }

        model.addAttribute("coleccion", coleccion);
        return "editarColeccion";
    }

    @PostMapping("/coleccion/editar/{id}")
    public String guardarColeccion(@PathVariable Long id,
                                   @ModelAttribute ColeccionDTO coleccionModificada,
                                   RedirectAttributes redirectAttrs) {

        System.out.println(">>> [CLIENTE LIVIANO] Entró a POST /coleccion/editar/" + id);

        try {
            // Log de DTO modificado recibido del form
            System.out.println(">>> DTO modificado recibido:");
            System.out.println("    ID: " + id);
            System.out.println("    Titulo: " + coleccionModificada.getTitulo());
            System.out.println("    Descripcion: " + coleccionModificada.getDescripcion());
            System.out.println("    Algoritmo: " + coleccionModificada.getAlgoritmoDeConsenso());

            // Seteamos el id
            coleccionModificada.setId(id);

            System.out.println(">>> Buscando coleccion original en el agregador...");
            ColeccionDTO coleccionOriginal = agregador.obtenerColeccionPorId(id);

            System.out.println(">>> Coleccion original encontrada:");
            System.out.println("    Titulo: " + coleccionOriginal.getTitulo());
            System.out.println("    Descripcion: " + coleccionOriginal.getDescripcion());
            System.out.println("    Algoritmo: " + coleccionOriginal.getAlgoritmoDeConsenso());

            System.out.println(">>> Llamando a agregador.saveOrUpdate()...");
            boolean ok = agregador.saveOrUpdate(coleccionOriginal, coleccionModificada);

            System.out.println(">>> Resultado saveOrUpdate = " + ok);

            redirectAttrs.addFlashAttribute("mensajeExito", "Colección editada correctamente");
        } catch (Exception e) {

            System.out.println(">>> ERROR en guardarColeccion()");
            e.printStackTrace();

            redirectAttrs.addFlashAttribute(
                    "mensajeError",
                    "Error al editar la colección: " + e.getMessage());
        }

        return "redirect:/admin/colecciones";
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

        return "redirect:/colecciones";
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

    @GetMapping("/hechos")
    public String listar(@RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        List<HechoDTO> hechos = agregador.obtenerHechosPendientes();
        List<CategoriaDTO> categorias = agregador.obtenerCategorias();
        model.addAttribute("categorias", categorias); // por si lo usás en algún badge, etc.

        int totalHechos = hechos.size();
        if (totalHechos == 0) {
            model.addAttribute("hechos", List.of());
            model.addAttribute("paginaActual", 1);
            model.addAttribute("paginaAnterior", 1);
            model.addAttribute("paginaSiguiente", 1);
            model.addAttribute("totalPaginas", 1);
            return "adminHechos";
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

        return "adminHechos";
    }

    @PostMapping("/hechos/{id}/aprobar")
    public String aprobarHecho(@PathVariable Long id,
                               RedirectAttributes redirectAttrs) {
        try {
            agregador.aprobarHecho(id);
            redirectAttrs.addFlashAttribute("mensajeExito", "Hecho aprobado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al aprobar el hecho: " + e.getMessage());
        }
        return "redirect:/admin/hechos";
    }

    // En tu AdminHechosController (cliente liviano)
    @PostMapping("/hechos/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            agregador.eliminarHecho(id);
            ra.addFlashAttribute("mensajeExito", "Hecho eliminado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("mensajeError", "No se pudo eliminar el hecho: " + e.getMessage());
        }
        return "redirect:/admin/hechos";
    }




}




