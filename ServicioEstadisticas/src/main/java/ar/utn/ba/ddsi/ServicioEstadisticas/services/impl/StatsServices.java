package ar.utn.ba.ddsi.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaHoraPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaProvinciaMasHechos;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaProvinciaPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IStatsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServices implements IStatsServices {
    private EstadisticaProvinciaMasHechos estadisticaProvinciaMasHechos;
    private EstadisticaHoraPorCategoria estadisticaHoraPorCategoria;
    private EstadisticaProvinciaPorCategoria estadisticaProvinciaPorCategoria;
    private EstadisticaProvinciaMasHechos estadisticaProvinciaMasHeches;
    @Autowired
    private ColeccionService coleccionService;

    public String obtenerProvinciaConMasHechos(Long coleccionId, String modoDeNavegacion) {
        List<Hecho> hechos = coleccionService.obtenerHechosDeColeccion(coleccionId, modoDeNavegacion);
        return estadisticaProvinciaMasHechos.calcular(hechos);
    }

    public String obtenerProvinciaPorCategoria(Long coleccionId, String categoria) {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        return estadisticaProvinciaPorCategoria.calcular(colecciones, categoria);
    }


}
