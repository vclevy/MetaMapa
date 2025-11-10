package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlgoritmoMultipleMenciones implements IAlgoritmo {

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

        // 1) Construimos un mapa: Hecho -> lista de fuentes que lo mencionan
        Map<Hecho, List<Fuente>> mapaHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            List<Hecho> hechosFuente = fuente.obtenerHechos();

            for (Hecho hecho : hechosFuente) {
                mapaHechos
                        .computeIfAbsent(hecho, h -> new ArrayList<>())
                        .add(fuente);
            }
        }

        // 2) Determinamos qué hechos cumplen la regla de múltiples menciones
        Set<Hecho> hechosQueCumplen = new HashSet<>();

        for (Map.Entry<Hecho, List<Fuente>> entrada : mapaHechos.entrySet()) {
            Hecho hecho = entrada.getKey();
            List<Fuente> fuentesQueLoMencionan = entrada.getValue();

            // Debe mencionarse en al menos 2 fuentes
            if (fuentesQueLoMencionan.size() < 2) {
                continue;
            }

            // Verificar si existe conflicto: otro hecho con mismo título pero distinto contenido
            boolean existeConflicto = fuentes.stream()
                    .flatMap(f -> f.obtenerHechos().stream())
                    .anyMatch(unHecho ->
                            unHecho.getTitulo().equals(hecho.getTitulo())
                                    && !unHecho.equals(hecho)
                    );

            if (!existeConflicto) {
                hechosQueCumplen.add(hecho);
            }
        }

        // 3) Marcar consenso en los HECHOS DE LA COLECCIÓN
        for (Hecho hechoColeccion : hechosDeColeccion) {
            boolean esConsensuado = hechosQueCumplen.contains(hechoColeccion);
            hechoColeccion.setEstaConsensuado(esConsensuado);
        }
    }
}
