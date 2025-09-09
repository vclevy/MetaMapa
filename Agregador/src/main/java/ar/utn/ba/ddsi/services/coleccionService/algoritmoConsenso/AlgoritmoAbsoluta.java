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
    public void aplicarConsenso(Coleccion unaColeccion) {
        List<Fuente> fuentes = unaColeccion.getFuentesDeHechos();

        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            for (Hecho hecho : fuente.obtenerHechos()) {
                if (tieneSolicitudAprobada(hecho)) continue;

                conteoHechos.put(hecho, conteoHechos.getOrDefault(hecho, 0) + 1);
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();

        for (Map.Entry<Hecho, Integer> entrada : conteoHechos.entrySet()) {
            if (entrada.getValue() == fuentes.size()) {
                hechosConsensuados.add(entrada.getKey());
            }
        }

        unaColeccion.setHechosConAlgotimoAplicado(hechosConsensuados);
    }

    private boolean tieneSolicitudAprobada(Hecho hecho) {
        if (hecho.getSolicitudesDeEliminacion() == null) return false;
        return hecho.getSolicitudesDeEliminacion().stream()
                .anyMatch(solicitud -> solicitud.getEstado() == EstadoDeSolicitudDeEliminacion.APROBADA);
    }

}
