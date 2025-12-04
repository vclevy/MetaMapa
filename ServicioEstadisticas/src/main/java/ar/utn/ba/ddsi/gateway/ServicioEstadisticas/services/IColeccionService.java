package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.services;

import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Coleccion;

import java.util.List;
public interface IColeccionService {
    List<Coleccion> obtenerColecciones();
}
