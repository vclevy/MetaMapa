package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/colecciones")
public class ColeccionController {

    @Autowired
    private AgregadorService agregador;
    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String listarColecciones(Model model) {
        // Obtener todas las colecciones
        List<ColeccionDTO> colecciones = agregador.obtenerColecciones();

        // Inicializar listas vacías para evitar nulls
        if (colecciones != null) {
            colecciones.forEach(c -> {
                if (c.getHechosDeLaColeccion() == null) {
                    c.setHechosDeLaColeccion(Collections.emptyList());
                }
            });
        }

        model.addAttribute("colecciones", colecciones);

        // Paginación (opcional, para uso futuro)
        int totalHechos = colecciones.size();
        int totalPaginas = (int) Math.ceil((double) totalHechos / PAGE_SIZE);
        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoColecciones"; // nombre del template Thymeleaf
    }

    @GetMapping("/{id}")
    public String verDetalleColeccion(@PathVariable Long id, Model model) {
        ColeccionDTO coleccion = agregador.obtenerColeccionPorId(id);

        model.addAttribute("coleccion", coleccion);

        return "detalleColeccion";
    }

}
