package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoMayoriaSimple implements IAlgoritmo {

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        List<Fuente> fuentes = coleccion.getFuentesDeHechos();
        List<Hecho> hechosDeColeccion = coleccion.getHechos();

        // Si no hay fuentes, nadie puede estar consensuado
        if (fuentes == null || fuentes.isEmpty()) {
            for (Hecho h : hechosDeColeccion) {
                h.setEstaConsensuado(false);
            }
            return;
        }

        // 1) Contamos cuántas fuentes mencionan cada hecho
        Map<Hecho, Integer> conteoHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            for (Hecho hechoFuente : fuente.obtenerHechos()) {
                conteoHechos.put(
                        hechoFuente,
                        conteoHechos.getOrDefault(hechoFuente, 0) + 1
                );
            }
        }

        // 2) Calculamos el umbral de mayoría simple: al menos la mitad de las fuentes
        int totalFuentes = fuentes.size();
        int umbral = (int) Math.ceil(totalFuentes / 2.0);

        // 3) Recorremos SOLO los hechos de la colección y marcamos si están consensuados
        for (Hecho hechoColeccion : hechosDeColeccion) {
            int menciones = conteoHechos.getOrDefault(hechoColeccion, 0);

            boolean esConsensuado = menciones >= umbral;
            hechoColeccion.setEstaConsensuado(esConsensuado);
        }
    }
}