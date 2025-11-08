package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoMultipleMenciones implements IAlgoritmo {
    @Override
    public List<Hecho> aplicarConsenso(Coleccion unaColeccion) {
        List<Fuente> fuentes = unaColeccion.getFuentesDeHechos();

        Map<Hecho, List<Fuente>> mapaHechos = new HashMap<>();

        for (Fuente fuente : fuentes) {
            List<Hecho> hechos = fuente.obtenerHechos();

            for (Hecho hecho : hechos) {
                mapaHechos
                        .computeIfAbsent(hecho, unHecho -> new ArrayList<>())  // Si el hecho ya está en el mapa, no hace nada
                        .add(fuente);  // Agrega la fuente al mapa del hecho
            }
        }

        List<Hecho> hechosConsensuados = new ArrayList<>();

        for (Map.Entry<Hecho, List<Fuente>> entrada : mapaHechos.entrySet()) {
            Hecho hecho = entrada.getKey();
            List<Fuente> fuentesQueLoMencionan = entrada.getValue();

            if (fuentesQueLoMencionan.size() >= 2) {

                // Verifica si existe un conflicto con otro hecho
                boolean existeConflicto = fuentes.stream()
                        .flatMap(unaFuente -> unaFuente.obtenerHechos().stream())
                        .anyMatch(unHecho -> unHecho.getTitulo().equals(hecho.getTitulo()) && !unHecho.equals(hecho));



                if (!existeConflicto) {
                    hechosConsensuados.add(hecho);
                }
            }
        }

        // Devolver los hechos consensuados, sin modificar directamente la colección
        return hechosConsensuados;
    }
}