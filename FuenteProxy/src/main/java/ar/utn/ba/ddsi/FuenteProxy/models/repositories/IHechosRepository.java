package ar.utn.ba.ddsi.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;

import java.util.List;
import java.util.Map;

public interface IHechosRepository {
        List<Hecho> findAll();

        Hecho findById(int id);

        void save(Hecho hecho);

        void delete(Hecho hecho);

        List<Hecho> obtenerHechosDeColeccion(String identificadorColeccion, Map<String, String> filtros);

        List<Hecho> obtenerHechos(Map<String, String> filtros);
}
