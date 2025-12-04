package ar.utn.ba.ddsi.gateway.cliente_liviano.controllers;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/colecciones")
public class ColeccionController {

    @Autowired
    private AgregadorService agregador;
    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String listarColecciones(Model model) {

        List<ColeccionDTO> colecciones = agregador.obtenerColecciones();

        if (colecciones != null) {
            colecciones.forEach(c -> {
                // Por cada colección → obtener hechos usando el agregador
                List<HechoDTO> hechosIrrestrictos =
                        agregador.obtenerHechosDeColeccion(c.getId(), "IRRESTRICTO");

                // Evitar nulls
                if (hechosIrrestrictos == null) {
                    hechosIrrestrictos = Collections.emptyList();
                }

                // Guardamos los hechos en el DTO
                c.setHechosDeLaColeccion(hechosIrrestrictos);
            });
        }

        model.addAttribute("colecciones", colecciones);

        // Si usás paginación, probablemente quieras contar colecciones, no hechos
        int totalColecciones = colecciones.size();
        int totalPaginas = (int) Math.ceil((double) totalColecciones / PAGE_SIZE);

        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoColecciones";
    }



    @GetMapping("/{id}")
    public String verDetalleColeccion(
            @PathVariable Long id,
            @RequestParam(name = "modo", defaultValue = "IRRESTRICTO") String modo,
            Model model) {

        ColeccionDTO coleccion = agregador.obtenerColeccionPorId(id);

        List<HechoDTO> hechos;
        if ("CURADO".equalsIgnoreCase(modo)) {
            // Curado sí aplica filtros / consenso
            hechos = agregador.obtenerHechosDeColeccion(id, "CURADO");
        } else {
            // IRRESTRICTO: devolvemos todos los hechos tal cual vienen en la colección
            hechos = agregador.obtenerHechosDeColeccion(id, "IRRESTRICTO");
        }

        model.addAttribute("coleccion", coleccion);
        model.addAttribute("hechos", hechos);
        model.addAttribute("modo", modo);

        return "detalleColeccion";
    }

}
