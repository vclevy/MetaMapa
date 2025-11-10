package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoAbsoluta implements IAlgoritmo {

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        List<Fuente> fuentes = coleccion.getFuentesDeHechos();
        List<Hecho> hechosDeColeccion = coleccion.getHechos();

        // Si no hay fuentes, nadie puede estar consensuado
        if (fuentes == null || fuentes.isEmpty()) {
            // Reseteamos todos a no consensuados
            for (Hecho h : hechosDeColeccion) {
                h.setEstaConsensuado(false);
            }
            return;
        }

        // 1) Contamos en cuántas fuentes aparece cada hecho
        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            for (Hecho hechoFuente : fuente.obtenerHechos()) {
                conteoHechos.put(
                        hechoFuente,
                        conteoHechos.getOrDefault(hechoFuente, 0) + 1
                );
            }
        }

        int totalFuentes = fuentes.size();

        // 2) Reseteamos y marcamos consenso SOLO en los hechos de la colección
        for (Hecho hechoColeccion : hechosDeColeccion) {
            int apariciones = conteoHechos.getOrDefault(hechoColeccion, 0);

            boolean esConsensuado = (apariciones == totalFuentes);
            hechoColeccion.setEstaConsensuado(esConsensuado);
        }
    }
}