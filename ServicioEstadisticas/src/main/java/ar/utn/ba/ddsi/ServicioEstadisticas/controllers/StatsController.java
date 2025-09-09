package ar.utn.ba.ddsi.ServicioEstadisticas.controllers;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.ColeccionService;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.StatsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class StatsController {
    @Autowired
    ColeccionService coleccionService;
    @Autowired
    StatsServices statsServices;

    @GetMapping("/todas")
    public List<ResultadoEstadistica> todas(@RequestParam(name = "categoria", required = true) String categoria) {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        return statsServices.calcularTodas(colecciones, categoria);
    }
    @GetMapping("/provinciaDominante")
    public List<ResultadoEstadistica> provinciaDominante() {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        System.out.printf("colecciones: %s\n", colecciones);
        return statsServices.provinciaConMasHechosDeUnaColeccion(colecciones);
    }

}
