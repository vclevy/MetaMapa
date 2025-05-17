package services;

import models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IProxyServices {
    List<Hecho> obtenerHechosDesdeAPI();

    List<Hecho> obtenerTodosLosHechos(Map<String, String> filtros);

    List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros);


}
