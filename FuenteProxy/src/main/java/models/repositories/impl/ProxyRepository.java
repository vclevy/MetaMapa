package models.repositories.impl;

import models.entities.Hecho;
import models.repositories.IProxyRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (hecho.getId() != null) {
            hechosMap.put(hecho.getId(), hecho);

            // Add to collection if category is specified
            if (hecho.getCategoria() != null && !hecho.getCategoria().isEmpty()) {
                coleccionesMap.computeIfAbsent(hecho.getCategoria(), k -> new ArrayList<>()).add(hecho);
            }
        }
    }

    @Override
    public void delete(Hecho hecho) {
        if (hecho.getId() != null) {
            hechosMap.remove(hecho.getId());

            // Remove from collection if category is specified
            if (hecho.getCategoria() != null && !hecho.getCategoria().isEmpty()) {
                List<Hecho> coleccion = coleccionesMap.get(hecho.getCategoria());
                if (coleccion != null) {
                    coleccion.removeIf(h -> h.getId().equals(hecho.getId()));
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
                        switch (filtro.getKey()) {
                            case "titulo":
                                if (!hecho.getTitulo().toLowerCase().contains(filtro.getValue().toLowerCase())) {
                                    return false;
                                }
                                break;
                            case "descripcion":
                                if (!hecho.getDescripcion().toLowerCase().contains(filtro.getValue().toLowerCase())) {
                                    return false;
                                }
                                break;
                            case "categoria":
                                if (!hecho.getCategoria().equalsIgnoreCase(filtro.getValue())) {
                                    return false;
                                }
                                break;
                            case "fuente":
                                if (!hecho.getFuente().equalsIgnoreCase(filtro.getValue())) {
                                    return false;
                                }
                                break;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
