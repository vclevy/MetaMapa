package ar.utn.ba.ddsi.FuenteProxy.models.repositories.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.IProxyRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProxyRepository implements IProxyRepository {

    // In-memory storage for hechos
    private final Map<String, Hecho> hechosMap = new HashMap<>();
    private final Map<String, List<Hecho>> coleccionesMap = new HashMap<>();

    @Override
    public List<Hecho> findAll() {
        return new ArrayList<>(hechosMap.values());
    }

    @Override
    public Hecho findById(int id) {
        return hechosMap.get(String.valueOf(id));
    }

    @Override
    public void save(Hecho hecho) {
        // Asumo que id es int primitivo, entonces chequeo > 0
        if (hecho.getId() > 0) {
            String key = String.valueOf(hecho.getId());
            hechosMap.put(key, hecho);

            // Agregar a coleccionesMap
            if (hecho.getCategoria() != null && !hecho.getCategoria().isEmpty()) {
                coleccionesMap.computeIfAbsent(hecho.getCategoria(), k -> new ArrayList<>());

                // Evitar duplicados
                List<Hecho> lista = coleccionesMap.get(hecho.getCategoria());
                // Remover si ya existe uno con ese ID para evitar duplicados
                lista.removeIf(h -> h.getId() == hecho.getId());
                lista.add(hecho);
            }
        }
    }

    @Override
    public void delete(Hecho hecho) {
        if (hecho.getId() > 0) {
            String key = String.valueOf(hecho.getId());
            hechosMap.remove(key);

            if (hecho.getCategoria() != null && !hecho.getCategoria().isEmpty()) {
                List<Hecho> coleccion = coleccionesMap.get(hecho.getCategoria());
                if (coleccion != null) {
                    coleccion.removeIf(h -> h.getId() == hecho.getId());
                }
            }
        }
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificadorColeccion, Map<String, String> filtros) {
        List<Hecho> hechos = coleccionesMap.getOrDefault(identificadorColeccion, new ArrayList<>());
        return aplicarFiltros(hechos, filtros);
    }

    @Override
    public List<Hecho> obtenerHechos(Map<String, String> filtros) {
        return aplicarFiltros(findAll(), filtros);
    }

    private List<Hecho> aplicarFiltros(List<Hecho> hechos, Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return hechos;
        }

        return hechos.stream()
                .filter(hecho -> {
                    for (Map.Entry<String, String> filtro : filtros.entrySet()) {
                        String key = filtro.getKey();
                        String valor = filtro.getValue();
                        if (valor == null) continue; // ignorar filtros nulos
                        switch (key) {
                            case "titulo":
                                if (hecho.getTitulo() == null || !hecho.getTitulo().toLowerCase().contains(valor.toLowerCase())) {
                                    return false;
                                }
                                break;
                            case "descripcion":
                                if (hecho.getDescripcion() == null || !hecho.getDescripcion().toLowerCase().contains(valor.toLowerCase())) {
                                    return false;
                                }
                                break;
                            case "categoria":
                                if (hecho.getCategoria() == null || !hecho.getCategoria().equalsIgnoreCase(valor)) {
                                    return false;
                                }
                                break;
                            default:
                                // Podés agregar más filtros si querés
                                break;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
