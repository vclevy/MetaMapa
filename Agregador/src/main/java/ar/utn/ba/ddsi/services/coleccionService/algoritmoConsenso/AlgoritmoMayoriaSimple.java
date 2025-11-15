package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AlgoritmoMayoriaSimple implements IAlgoritmo {
    @Autowired
    private IFuenteDeHechosRepository fuenteDeHechosRepository;
    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
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

        int totalFuentes = todasLasFuentes.size();
        int minimoParaMayoria = (int) Math.ceil(totalFuentes / 2.0);

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

        // Para cada hecho de la colección, contamos en cuántas fuentes aparece
        for (Hecho hechoColeccion : hechosDeColeccion) {
            String claveHechoColeccion = construirClave(
                    hechoColeccion.getTitulo(),
                    hechoColeccion.getDescripcion()
            );

            int cantidadFuentesQueLoContienen = 0;

            for (Fuente fuente : todasLasFuentes) {
                Set<String> clavesDeEstaFuente = clavesPorFuente.get(fuente.getId());

                if (clavesDeEstaFuente != null && clavesDeEstaFuente.contains(claveHechoColeccion)) {
                    cantidadFuentesQueLoContienen++;
                }
            }

            boolean estaConsensuado = cantidadFuentesQueLoContienen >= minimoParaMayoria;
            hechoColeccion.setEstaConsensuado(estaConsensuado);
        }

        hechosRepository.saveAll(hechosDeColeccion);
    }

    private String construirClave(String titulo, String descripcion) {
        return (titulo == null ? "" : titulo) + "||" + (descripcion == null ? "" : descripcion);
    }
}
