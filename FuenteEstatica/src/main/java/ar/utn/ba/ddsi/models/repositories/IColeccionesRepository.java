package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.repositories;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Coleccion;

public interface IColeccionesRepository {

    Coleccion crearColeccion(String titulo, String descripcion);
}

