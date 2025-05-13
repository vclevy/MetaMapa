package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.services;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Coleccion;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.filtroHecho.FiltroHecho;

public interface IColeccionServices
{
    Coleccion crearColeccion(String titulo, String descripcion);

    Coleccion filtrarColeccion(Coleccion coleccion, FiltroHecho filtro);
}
