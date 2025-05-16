package models.repositories.impl;


import models.entities.Hecho;
import models.repositories.IProxyRepository;

import java.util.List;
import java.util.Map;

public class ProxyRepository implements IProxyRepository {
    @Override
    public List<Hecho> findAll() {
        return List.of();
    }

    @Override
    public Hecho findById(int id) {
        return null;
    }

    @Override
    public void save(Hecho hecho) {

    }

    @Override
    public void delete(Hecho hecho) {

    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificadorColeccion, Map<String, String> filtros) {
        return List.of();
    }

    @Override
    public List<Hecho> obtenerHechos(Map<String, String> filtros) {
        return List.of();
    }


}
