package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.repositories;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;

import java.util.List;

public interface IHechosRepository {
    List<Hecho> findAll();
    Hecho findById(int id);
    void save(Hecho hecho);
    void delete(Hecho hecho);
}
