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
    public List<Hecho> aplicarConsenso(Coleccion unaColeccion) {
        List<Fuente> fuentes = unaColeccion.getFuentesDeHechos();

        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            for (Hecho hecho : fuente.obtenerHechos()) {
                // Aquí puedes aplicar la lógica de eliminación o conteo según sea necesario
                if (tieneSolicitudAprobada(hecho)) continue;

                conteoHechos.put(hecho, conteoHechos.getOrDefault(hecho, 0) + 1);
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();
        for (Map.Entry<Hecho, Integer> entry : conteoHechos.entrySet()) {
            if (entry.getValue() == fuentes.size()) {
                hechosConsensuados.add(entry.getKey());

            }
        }
        return hechosConsensuados;  // Devolvemos la lista de hechos consensuados

    }

    private boolean tieneSolicitudAprobada(Hecho hecho) {
        return hecho.getSolicitudesDeEliminacion().stream()
                .anyMatch(s -> s.getEstado() == EstadoDeSolicitudDeEliminacion.APROBADA);
    }

}