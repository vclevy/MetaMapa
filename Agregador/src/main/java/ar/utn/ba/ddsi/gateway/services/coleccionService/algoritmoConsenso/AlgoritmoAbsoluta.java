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
    private IFuenteDeHechosRepository fuenteDeHechosRepository;
    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        // Hechos que componen la colección
        List<Hecho> hechosDeColeccion = coleccion.getHechos();
        if (hechosDeColeccion == null || hechosDeColeccion.isEmpty()) {
            return;
        }

        // Todas las fuentes existentes en el sistema
        List<Fuente> todasLasFuentes = fuenteDeHechosRepository.findAll();

        if (todasLasFuentes.isEmpty()) {
            // Si no hay fuentes, nadie puede estar consensuado
            for (Hecho h : hechosDeColeccion) {
                h.setEstaConsensuado(false);
            }
            hechosRepository.saveAll(hechosDeColeccion);
            return;
        }

        // Para cada fuente, armamos un SET de "hechos lógicos" (titulo+descripcion)
        // para poder preguntar rápido si un hecho de la colección existe en esa fuente
        Map<Long, Set<String>> clavesPorFuente = new HashMap<>();

        for (Fuente fuente : todasLasFuentes) {
            List<Hecho> hechosDeFuente = fuente.getHechos();

            Set<String> clavesHechos = hechosDeFuente.stream()
                    .map(h -> construirClave(h.getTitulo(), h.getDescripcion()))
                    .collect(Collectors.toSet());

            clavesPorFuente.put(fuente.getId(), clavesHechos);
        }

        for (Hecho hechoColeccion : hechosDeColeccion) {
            String claveHechoColeccion = construirClave(
                    hechoColeccion.getTitulo(),
                    hechoColeccion.getDescripcion()
            );

            boolean estaEnTodas = true;

            for (Fuente fuente : todasLasFuentes) {
                Set<String> clavesDeEstaFuente = clavesPorFuente.get(fuente.getId());

                if (clavesDeEstaFuente == null || !clavesDeEstaFuente.contains(claveHechoColeccion)) {
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