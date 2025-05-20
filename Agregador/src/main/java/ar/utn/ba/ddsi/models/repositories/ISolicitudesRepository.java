package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;

import java.util.List;

public interface ISolicitudesRepository {
    public List<Solicitud> findAll();
    public Solicitud findById(Integer id);
    public void save(Solicitud solicitud);
    public void delete(Solicitud solicitud);
}