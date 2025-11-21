package ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.gateway.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.gateway.models.repositories.IHechosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AlgoritmoAbsoluta implements IAlgoritmo {

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        List<Hecho> hechosDeColeccion = coleccion.getHechos();
        if (hechosDeColeccion == null || hechosDeColeccion.isEmpty()) {
            return;
        }

        // Usar SOLO las fuentes de la colección
        List<Fuente> fuentesColeccion = coleccion.getFuentesDeHechos();

        // Regla extra: con menos de 2 fuentes NO hay consenso
        if (fuentesColeccion == null || fuentesColeccion.size() < 2) {
            hechosDeColeccion.forEach(h -> h.setEstaConsensuado(false));
            hechosRepository.saveAll(hechosDeColeccion);
            return;
        }

        // Construimos sets de claves para cada fuente
        Map<Long, Set<String>> clavesPorFuente = new HashMap<>();

        for (Fuente fuente : fuentesColeccion) {
            List<Hecho> hechosDeFuente = fuente.getHechos();

            Set<String> clavesHechos = hechosDeFuente.stream()
                    .map(h -> construirClave(h.getTitulo(), h.getDescripcion()))
                    .collect(Collectors.toSet());

            clavesPorFuente.put(fuente.getId(), clavesHechos);
        }

        // Evaluamos cada hecho
        for (Hecho hechoColeccion : hechosDeColeccion) {

            String claveHechoColeccion = construirClave(
                    hechoColeccion.getTitulo(),
                    hechoColeccion.getDescripcion()
            );

            boolean estaEnTodas = true;

            for (Fuente fuente : fuentesColeccion) {
                Set<String> claves = clavesPorFuente.get(fuente.getId());

                if (claves == null || !claves.contains(claveHechoColeccion)) {
                    estaEnTodas = false;
                    break;
                }
            }

            hechoColeccion.setEstaConsensuado(estaEnTodas);
        }

        hechosRepository.saveAll(hechosDeColeccion);
    }

    private String construirClave(String titulo, String descripcion) {
        return (titulo == null ? "" : titulo) + "||" + (descripcion == null ? "" : descripcion);
    }
}