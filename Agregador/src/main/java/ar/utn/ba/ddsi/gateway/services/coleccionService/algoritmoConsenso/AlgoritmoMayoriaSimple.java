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
public class AlgoritmoMayoriaSimple implements IAlgoritmo {
    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        List<Hecho> hechosDeColeccion = coleccion.getHechos();
        if (hechosDeColeccion == null || hechosDeColeccion.isEmpty()) return;

        List<Fuente> fuentesColeccion = coleccion.getFuentesDeHechos();
        if (fuentesColeccion == null || fuentesColeccion.size() < 2) {
            hechosDeColeccion.forEach(h -> h.setEstaConsensuado(false));
            hechosRepository.saveAll(hechosDeColeccion);
            return;
        }

        int totalFuentes = fuentesColeccion.size();
        int minimoParaMayoria = (int) Math.ceil(totalFuentes / 2.0);

        Map<Long, Set<String>> clavesPorFuente = new HashMap<>();
        for (Fuente fuente : fuentesColeccion) {
            Set<String> clavesHechos = fuente.getHechos().stream()
                    .map(h -> construirClave(h.getTitulo(), h.getDescripcion()))
                    .collect(Collectors.toSet());
            clavesPorFuente.put(fuente.getId(), clavesHechos);
        }

        for (Hecho hechoColeccion : hechosDeColeccion) {
            String claveHechoColeccion = construirClave(
                    hechoColeccion.getTitulo(),
                    hechoColeccion.getDescripcion()
            );

            int cantidadFuentesQueLoContienen = 0;
            for (Fuente fuente : fuentesColeccion) {
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
