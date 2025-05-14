package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.solicitudes.Solicitud;

import java.util.List;

public interface ISolicitudesRepository {
    List<Solicitud> findAll();
    Solicitud findById(int id);
    void save(Solicitud hecho);
    void delete(Solicitud hecho);
}
