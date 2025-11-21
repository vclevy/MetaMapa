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
public class AlgoritmoMultipleMenciones implements IAlgoritmo {

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void aplicarConsenso(Coleccion coleccion) {
        // Hechos que componen la colección
        List<Hecho> hechosDeColeccion = coleccion.getHechos();
        if (hechosDeColeccion == null || hechosDeColeccion.isEmpty()) {
            return;
        }

        // Usamos SOLO las fuentes asociadas a la colección
        List<Fuente> fuentesColeccion = coleccion.getFuentesDeHechos();

        // Regla: con menos de 2 fuentes NO hay consenso
        if (fuentesColeccion == null || fuentesColeccion.size() < 2) {
            for (Hecho h : hechosDeColeccion) {
                h.setEstaConsensuado(false);
            }
            hechosRepository.saveAll(hechosDeColeccion);
            return;
        }

        // Para cada fuente, armamos un SET de "hechos lógicos" (titulo+descripcion)
        Map<Long, Set<String>> clavesPorFuente = new HashMap<>();

        // Además, armamos un mapa global titulo -> set de descripciones
        // para detectar conflictos (mismo título, distintas descripciones)
        Map<String, Set<String>> descripcionesPorTitulo = new HashMap<>();

        for (Fuente fuente : fuentesColeccion) {
            List<Hecho> hechosDeFuente = fuente.getHechos();

            Set<String> clavesHechos = hechosDeFuente.stream()
                    .map(h -> {
                        String clave = construirClave(h.getTitulo(), h.getDescripcion());

                        // llenamos también el mapa titulo -> descripciones
                        descripcionesPorTitulo
                                .computeIfAbsent(
                                        h.getTitulo() == null ? "" : h.getTitulo(),
                                        t -> new HashSet<>()
                                )
                                .add(h.getDescripcion() == null ? "" : h.getDescripcion());

                        return clave;
                    })
                    .collect(Collectors.toSet());

            clavesPorFuente.put(fuente.getId(), clavesHechos);
        }

        // Para cada hecho de la colección aplicamos la regla:
        // - aparece en al menos 2 fuentes
        // - y no hay otra descripción distinta para el mismo título
        for (Hecho hechoColeccion : hechosDeColeccion) {

            String titulo = hechoColeccion.getTitulo();
            String descripcion = hechoColeccion.getDescripcion();
            String claveHechoColeccion = construirClave(titulo, descripcion);

            // 1) Contar en cuántas fuentes aparece este hecho (titulo+descripcion)
            int cantidadFuentesQueLoContienen = 0;

            for (Fuente fuente : fuentesColeccion) {
                Set<String> clavesDeEstaFuente = clavesPorFuente.get(fuente.getId());

                if (clavesDeEstaFuente != null && clavesDeEstaFuente.contains(claveHechoColeccion)) {
                    cantidadFuentesQueLoContienen++;
                }
            }

            boolean alMenosDosFuentes = cantidadFuentesQueLoContienen >= 2;

            // 2) Verificar que no haya otra descripción distinta para el mismo título
            Set<String> descripcionesParaTitulo = descripcionesPorTitulo.getOrDefault(
                    titulo == null ? "" : titulo,
                    Collections.emptySet()
            );

            // Si hay más de una descripción distinta asociada a ese título, hay conflicto
            boolean sinConflictos = descripcionesParaTitulo.size() <= 1;

            boolean estaConsensuado = alMenosDosFuentes && sinConflictos;
            hechoColeccion.setEstaConsensuado(estaConsensuado);
        }

        hechosRepository.saveAll(hechosDeColeccion);
    }

    private String construirClave(String titulo, String descripcion) {
        return (titulo == null ? "" : titulo) + "||" + (descripcion == null ? "" : descripcion);
    }
}
