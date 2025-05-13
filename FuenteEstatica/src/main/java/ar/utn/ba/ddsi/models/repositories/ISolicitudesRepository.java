package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.repositories;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.solicitudes.Solicitud;

import java.util.List;

public interface ISolicitudesRepository {
    List<Solicitud> findAll();
    Solicitud findById(int id);
    void save(Solicitud hecho);
    void delete(Solicitud hecho);
}
